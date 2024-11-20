package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.gov.diia.client.example.model.BranchOffers;
import ua.gov.diia.client.example.model.SharingByDeeplinkRequest;
import ua.gov.diia.client.example.util.QR;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.Offer;
import ua.gov.diia.client.sdk.remote.model.OfferList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class RequestingDocumentByDeeplinkController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestingDocumentByDeeplinkController.class);

    private final Diia diia;

    public RequestingDocumentByDeeplinkController(Diia diia) {
        this.diia = diia;
    }

    @GetMapping("/requesting-by-deeplink")
    public ModelAndView getDeepLinkBegin() {
        try {
            BranchList branches = diia.getBranches(null, null);
            List<BranchOffers> branchesOffers = branches.getBranches().stream()
                    .filter(b -> b.getScopes().getSharing() != null)
                    .filter(b -> !b.getScopes().getSharing().isEmpty())
                    .map(b -> {
                        OfferList offerList = diia.getOffers(b.getId().getId(), null, null);
                        List<Offer> offers = offerList.getOffers().stream()
                                .filter(o -> o.getScopes().getSharing() != null)
                                .filter(o -> !o.getScopes().getSharing().isEmpty())
                                .collect(Collectors.toList());
                        return new BranchOffers(b, offers);
                    })
                    .filter(bo -> !bo.getOffers().isEmpty())
                    .collect(Collectors.toList());
            if (branchesOffers.isEmpty()) {
                return new ModelAndView("sharing/requesting-not-available-offers");
            }
            ModelAndView mv = new ModelAndView("sharing/begin-requesting-by-deeplink");
            mv.addObject("branchesOffers", branchesOffers);
            mv.addObject("requestId", UUID.randomUUID().toString());
            return mv;
        } catch (DiiaClientException e) {
            LOGGER.error("Can't create deeplink", e);
            ModelAndView mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
            return mv;
        }
    }

    @PostMapping("/requesting-by-deeplink")
    public ModelAndView getDeeplink(SharingByDeeplinkRequest request) {
        ModelAndView mv;
        try {
            String[] branchAndOfferId = request.getBranchAndOfferId().split("!");
            String branchId = branchAndOfferId[0];
            String offerId = branchAndOfferId[1];
            String requestId = request.getRequestId();

            String deepLink = diia.getDeepLink(branchId, offerId, requestId);

            String qrCodeImageBase64 = QR.generateQRFromUrl(deepLink);

            mv = new ModelAndView("sharing/requesting-by-deeplink");
            mv.addObject("requestId", requestId);
            mv.addObject("deeplink", deepLink);
            mv.addObject("qrCodeImageBase64", qrCodeImageBase64);
        } catch (DiiaClientException e) {
            LOGGER.error("Can't create deeplink", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }
}
