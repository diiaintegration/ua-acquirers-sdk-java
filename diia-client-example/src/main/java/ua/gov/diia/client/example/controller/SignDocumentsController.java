package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.gov.diia.client.example.model.BranchOffers;
import ua.gov.diia.client.example.model.SignDocumentsRequest;
import ua.gov.diia.client.example.util.QR;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.model.FileData;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.Offer;
import ua.gov.diia.client.sdk.remote.model.OfferList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class SignDocumentsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignDocumentsController.class);
    private static final String SCOPE_HASHED_FILES_SIGNING = "hashedFilesSigning";

    private final Diia diia;

    public SignDocumentsController(Diia diia) {
        this.diia = diia;
    }

    @GetMapping("/sign")
    public ModelAndView requestingPage() {
        BranchList branches = diia.getBranches(null, null);
        List<BranchOffers> branchesOffers = branches.getBranches().stream()
                .filter(b -> b.getScopes().getDiiaId() != null)
                .filter(b -> b.getScopes().getDiiaId().contains(SCOPE_HASHED_FILES_SIGNING))
                .map(b -> {
                    OfferList offerList = diia.getOffers(b.getId().getId(), null, null);
                    List<Offer> offers = offerList.getOffers().stream()
                            .filter(o -> o.getScopes().getDiiaId() != null)
                            .filter(o -> o.getScopes().getDiiaId().contains(SCOPE_HASHED_FILES_SIGNING))
                            .collect(Collectors.toList());
                    return new BranchOffers(b, offers);
                })
                .filter(bo -> !bo.getOffers().isEmpty())
                .collect(Collectors.toList());
        if (branchesOffers.isEmpty()) {
            return new ModelAndView("sign/sign-documents-not-available-offers");
        }
        ModelAndView mv = new ModelAndView("sign/sign-documents");
        mv.addObject("branchesOffers", branchesOffers);
        mv.addObject("requestId", UUID.randomUUID().toString());
        return mv;
    }

    @PostMapping("/sign")
    public ModelAndView signDocuments(@ModelAttribute SignDocumentsRequest request) {
        ModelAndView mv;
        try {
            List<FileData> files = Arrays.stream(request.getFiles())
                    .map(mp -> {
                        try {
                            FileData fileData = new FileData();
                            fileData.setData(mp.getBytes());
                            fileData.setFileName(mp.getOriginalFilename());
                            return fileData;
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .collect(Collectors.toList());
            String[] branchAndOfferId = request.getBranchAndOfferId().split("!");
            String branchId = branchAndOfferId[0];
            String offerId = branchAndOfferId[1];
            String signDeepLink = diia.getSignDeepLink(branchId, offerId, request.getRequestId(), files);
            mv = new ModelAndView("sign/sign-documents-deeplink");
            mv.addObject("deeplink", signDeepLink);
            mv.addObject("qrCodeImageBase64", QR.generateQRFromUrl(signDeepLink));
            mv.addObject("requestId", request.getRequestId());
        } catch (DiiaClientException e) {
            LOGGER.error("Can't request document", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }
}
