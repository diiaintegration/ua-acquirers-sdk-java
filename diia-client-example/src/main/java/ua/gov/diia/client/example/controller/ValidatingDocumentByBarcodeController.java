package ua.gov.diia.client.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.gov.diia.client.example.model.ValidatingDocumentRequest;
import ua.gov.diia.client.sdk.Diia;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ValidatingDocumentByBarcodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatingDocumentByBarcodeController.class);

    private final Diia diia;

    public ValidatingDocumentByBarcodeController(Diia diia) {
        this.diia = diia;
    }

    @GetMapping("/validating")
    public ModelAndView validatingPage() {
        ModelAndView mv;
        try {
            BranchList branchList = diia.getBranches(null, null);
            List<Branch> branches = branchList.getBranches().stream()
                    .filter(b -> b.getScopes().getDocumentIdentification() != null)
                    .filter(b -> !b.getScopes().getDocumentIdentification().isEmpty())
                    .collect(Collectors.toList());
            mv = new ModelAndView("validation/validating-by-barcode");
            mv.addObject("branches", branches);
        } catch (DiiaClientException e) {
            LOGGER.error("Can't validate document", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }

    @PostMapping("/validating")
    public ModelAndView doValidation(@ModelAttribute ValidatingDocumentRequest validatingDocumentRequest) {
        ModelAndView mv;
        try {
            String barcode = validatingDocumentRequest.getBarcode();
            String branchId = validatingDocumentRequest.getBranchId();

            boolean isDocumentValid = diia.validateDocumentByBarcode(branchId, barcode);
            mv = new ModelAndView(isDocumentValid ? "validation/validation-success" : "validation/validation-failed");
        } catch (DiiaClientException e) {
            LOGGER.error("Can't validate document", e);
            mv = new ModelAndView("exception");
            mv.addObject("message", e.getMessage());
        }
        return mv;
    }
}
