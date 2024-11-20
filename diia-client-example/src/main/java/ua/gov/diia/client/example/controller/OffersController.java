package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;
import ua.gov.diia.client.example.model.BranchOffers;
import ua.gov.diia.client.example.model.CreateOfferRequest;
import ua.gov.diia.client.example.model.DeleteOfferRequest;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.OfferList;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class OffersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OffersController.class);

    private final Diia diia;

    public OffersController(Diia diia) {
        this.diia = diia;
    }

    @GetMapping("/all-offers")
    public ModelAndView allOffers() {
        ModelAndView mv;

        BranchList branches = diia.getBranches(null, null);
        if (branches.getBranches().isEmpty()) {
            mv = new ModelAndView("exception");
            mv.addObject("message", "There is no branches created");
        } else {
            Collection<BranchOffers> branchesOffers = new ArrayList<>();
            for (Branch branch : branches.getBranches()) {
                OfferList offers = diia.getOffers(branch.getId().getId(), null, null);
                branchesOffers.add(new BranchOffers(branch, offers.getOffers()));
            }

            mv = new ModelAndView("offers/all-offers");

            mv.addObject("branchesOffers", branchesOffers);
        }
        return mv;
    }

    @GetMapping("/create-offer")
    public ModelAndView createOfferPage() {
        ModelAndView mv;

        BranchList branches = diia.getBranches(null, null);
        if (branches.getBranches().isEmpty()) {
            mv = new ModelAndView("exception");
            mv.addObject("message", "There is no branches created");
        } else {
            mv = new ModelAndView("offers/create-offer");
            mv.addObject("branches", branches.getBranches());
        }
        return mv;
    }

    @PostMapping("/create-offer")
    public ModelAndView doCreateOffer(@ModelAttribute CreateOfferRequest createOfferRequest) {
        ModelAndView mv;
        try {
            List<String> sharing = null;
            if (createOfferRequest.getSharing() != null && !createOfferRequest.getSharing().isEmpty()) {
                sharing = Arrays.stream(createOfferRequest.getSharing().split(","))
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            }

            List<String> diiaId = null;
            if (createOfferRequest.getDiiaId() != null && !createOfferRequest.getDiiaId().isEmpty()) {
                diiaId = Arrays.stream(createOfferRequest.getDiiaId().split(","))
                        .filter(Objects::nonNull)
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            }

            String name = createOfferRequest.getName();
            String returnLink = StringUtils.isEmpty(createOfferRequest.getReturnLink()) ? null : createOfferRequest.getReturnLink();

            String offerId = diia.createOffer(createOfferRequest.getBranchId(), name, returnLink, sharing, diiaId);

            mv = new ModelAndView("offers/create-offer-success");
            mv.addObject("branchId", createOfferRequest.getBranchId());
            mv.addObject("offerId", offerId);

        } catch (Exception e) {
            LOGGER.error("Can't create offer", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }

    @GetMapping("/delete-offer")
    public ModelAndView deleteOfferPage() {
        ModelAndView mv;

        BranchList branches = diia.getBranches(null, null);
        if (branches.getBranches().isEmpty()) {
            mv = new ModelAndView("exception");
            mv.addObject("message", "There is no branches created");
        } else {
            Collection<BranchOffers> branchesOffers = new ArrayList<>();
            for (Branch branch : branches.getBranches()) {
                OfferList offers = diia.getOffers(branch.getId().getId(), null, null);
                branchesOffers.add(new BranchOffers(branch, offers.getOffers()));
            }
            mv = new ModelAndView("offers/delete-offer");
            mv.addObject("branchesOffers", branchesOffers);
        }
        return mv;
    }

    @PostMapping("/delete-offer")
    public ModelAndView doDeleteOffer(@ModelAttribute DeleteOfferRequest deleteOfferRequest) {
        ModelAndView mv;

        try {
            String[] branchAndOfferId = deleteOfferRequest.getBranchAndOfferId().split("!");
            String branchId = branchAndOfferId[0];
            String offerId = branchAndOfferId[1];
            mv = new ModelAndView("offers/delete-offer-success");
            mv.addObject("branchId", branchId);
            diia.deleteOffer(branchId, offerId);
        } catch (Exception e) {
            LOGGER.error("Can't delete offer", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }

}
