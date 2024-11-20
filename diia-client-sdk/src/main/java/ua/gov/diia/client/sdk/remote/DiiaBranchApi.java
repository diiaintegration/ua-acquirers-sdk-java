package ua.gov.diia.client.sdk.remote;

import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.Id;

public interface DiiaBranchApi {
    Id createBranch(Branch request);
    Branch getBranchById(String branchId);
    void deleteBranchById(String branchId);
    BranchList getBranches(Long skip, Long limit);
    Branch updateBranch(Branch branch);
}
