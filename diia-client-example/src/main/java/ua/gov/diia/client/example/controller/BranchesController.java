package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.gov.diia.client.example.model.CreateBranchRequest;
import ua.gov.diia.client.example.model.DeleteBranchRequest;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.BranchScopes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class BranchesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BranchesController.class);

    private final Diia diia;

    public BranchesController(Diia diia) {
        this.diia = diia;
    }

    @GetMapping("/all-branches")
    public ModelAndView allBranches() {
        ModelAndView mv = new ModelAndView("branches/all-branches");
        mv.addObject("branches", diia.getBranches(null, null).getBranches());
        return mv;
    }

    @GetMapping("/create-branch")
    public ModelAndView createBranchPage() {
        ModelAndView mv = new ModelAndView("branches/create-branch");
        return mv;
    }

    @PostMapping("/create-branch")
    public ModelAndView doCreateBranch(@ModelAttribute CreateBranchRequest createBranchRequest) {
        ModelAndView mv;
        try {
            Branch branch = new Branch();
            branch.setName(createBranchRequest.getName());
            branch.setEmail(createBranchRequest.getEmail());
            branch.setRegion(createBranchRequest.getRegion());
            branch.setDistrict(createBranchRequest.getDistrict());
            branch.setLocation(createBranchRequest.getLocation());
            branch.setStreet(createBranchRequest.getStreet());
            branch.setHouse(createBranchRequest.getHouse());
            branch.setCustomFullName(createBranchRequest.getCustomFullName());
            branch.setCustomFullAddress(createBranchRequest.getCustomFullAddress());
            branch.setOfferRequestType("dynamic");
            branch.setDeliveryTypes(Collections.singletonList("api"));

            BranchScopes scopes = new BranchScopes();
            scopes.setIdentification(Collections.emptyList());
            scopes.setSharing(Arrays.stream(createBranchRequest.getSharing().split(","))
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .collect(Collectors.toList()));
            scopes.setDocumentIdentification(Arrays.stream(createBranchRequest.getDocumentIdentification().split(","))
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .collect(Collectors.toList()));
            scopes.setDiiaId(Arrays.stream(createBranchRequest.getDiiaId().split(","))
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .collect(Collectors.toList())
            );

            branch.setScopes(scopes);

            String createdBranchId = diia.createBranch(branch);

            mv = new ModelAndView("branches/create-branch-success");
            mv.addObject("branchId", createdBranchId);
        } catch (Exception e) {
            LOGGER.error("Can't create offer", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }

    @GetMapping("/delete-branch")
    public ModelAndView deleteBranchPage() {
        ModelAndView mv;
        BranchList branches = diia.getBranches(null, null);
        if (branches.getBranches().isEmpty()) {
            mv = new ModelAndView("exception");
            mv.addObject("message", "There is no branches created");
        } else {
            mv = new ModelAndView("branches/delete-branch");
            mv.addObject("branches", branches.getBranches());
        }
        return mv;
    }

    @PostMapping("/delete-branch")
    public ModelAndView doDeleteBranch(@ModelAttribute DeleteBranchRequest deleteBranchRequest) {
        String branchId = deleteBranchRequest.getBranchId();

        ModelAndView mv;
        try {
            diia.deleteBranch(branchId);
            mv = new ModelAndView("branches/delete-branch-success");
        } catch (Exception e) {
            LOGGER.error("Can't delete branch", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }
}
