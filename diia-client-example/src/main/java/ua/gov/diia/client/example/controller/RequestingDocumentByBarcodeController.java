package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.gov.diia.client.example.model.RequestingDocumentRequest;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class RequestingDocumentByBarcodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestingDocumentByBarcodeController.class);

    private final Diia diia;

    public RequestingDocumentByBarcodeController(Diia diia) {
        this.diia = diia;
    }

    @GetMapping("/requesting")
    public ModelAndView requestingPage() {
        try {
            BranchList branchList = diia.getBranches(null, null);
            List<Branch> branches = branchList.getBranches().stream()
                    .filter(b -> b.getScopes().getSharing() != null)
                    .filter(b -> !b.getScopes().getSharing().isEmpty())
                    .collect(Collectors.toList());
            if (branches.isEmpty()) {
                return new ModelAndView("sharing/requesting-not-available-offers");
            }
            ModelAndView mv = new ModelAndView("sharing/requesting-by-barcode");
            mv.addObject("branches", branches);
            mv.addObject("requestId", UUID.randomUUID().toString());
            return mv;
        } catch (DiiaClientException e) {
            LOGGER.error("Can't validate document", e);
            ModelAndView mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
            return mv;
        }
    }

    @PostMapping("/requesting")
    public ModelAndView doRequestDocumentByBarcode(@ModelAttribute RequestingDocumentRequest requestingDocumentRequest) {
        ModelAndView mv;
        try {
            String branchId = requestingDocumentRequest.getBranchId();
            String barcode = requestingDocumentRequest.getBarcode();
            String requestId = requestingDocumentRequest.getRequestId();

            boolean isDocumentValid = diia.requestDocumentByBarCode(branchId, barcode, requestId);
            mv = new ModelAndView(isDocumentValid ? "sharing/request-success" : "sharing/request-failed");
            mv.addObject("requestId", requestId);
        } catch (DiiaClientException e) {
            LOGGER.error("Can't request document", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }
}
