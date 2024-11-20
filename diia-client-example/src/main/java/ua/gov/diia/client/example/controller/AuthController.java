package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.gov.diia.client.example.model.AuthRequest;
import ua.gov.diia.client.example.model.BranchOffers;
import ua.gov.diia.client.example.util.QR;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.model.AuthDeepLink;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.Offer;
import ua.gov.diia.client.sdk.remote.model.OfferList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private static final String SCOPE_AUTH = "auth";

    private final Diia diia;

    public AuthController(Diia diia) {
        this.diia = diia;
    }

    @GetMapping("/auth")
    public ModelAndView requestingPage() {
        BranchList branches = diia.getBranches(null, null);
        List<BranchOffers> branchesOffers = branches.getBranches().stream()
                .filter(b -> b.getScopes().getDiiaId() != null)
                .filter(b -> b.getScopes().getDiiaId().contains(SCOPE_AUTH))
                .map(b -> {
                    OfferList offerList = diia.getOffers(b.getId().getId(), null, null);
                    List<Offer> offers = offerList.getOffers().stream()
                            .filter(o -> o.getScopes().getDiiaId() != null)
                            .filter(o -> o.getScopes().getDiiaId().contains(SCOPE_AUTH))
                            .collect(Collectors.toList());
                    return new BranchOffers(b, offers);
                })
                .filter(bo -> !bo.getOffers().isEmpty())
                .collect(Collectors.toList());
        if (branchesOffers.isEmpty()) {
            return new ModelAndView("auth/auth-not-available-offers");
        }
        ModelAndView mv = new ModelAndView("auth/auth");
        mv.addObject("branchesOffers", branchesOffers);
        mv.addObject("requestId", UUID.randomUUID().toString());
        return mv;
    }

    @PostMapping("/auth")
    public ModelAndView auth(@ModelAttribute AuthRequest request) {
        ModelAndView mv;
        try {
            String[] branchAndOfferId = request.getBranchAndOfferId().split("!");
            String branchId = branchAndOfferId[0];
            String offerId = branchAndOfferId[1];
            AuthDeepLink authDeepLink = diia.getAuthDeepLink(branchId, offerId, request.getRequestId());
            mv = new ModelAndView("auth/auth-deeplink");
            mv.addObject("deeplink", authDeepLink.getDeepLink());
            mv.addObject("qrCodeImageBase64", QR.generateQRFromUrl(authDeepLink.getDeepLink()));
            mv.addObject("requestIdHash", authDeepLink.getRequestIdHash().replaceAll("\\W+", "_"));
        } catch (DiiaClientException e) {
            LOGGER.error("Can't request document", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }
}
