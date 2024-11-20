package ua.gov.diia.client.sdk.service;

import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;

public interface BranchService {
    BranchList getBranches(Long skip, Long limit);
    Branch getBranch(String branchId);
    void deleteBranch(String branchId);
    String createBranch(Branch request);
    Branch updateBranch(Branch request);
}
