package ua.gov.diia.client.sdk.service.impl;

import ua.gov.diia.client.sdk.remote.DiiaBranchApi;
import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.Id;
import ua.gov.diia.client.sdk.service.BranchService;

public class BranchServiceImpl implements BranchService {
    private final DiiaBranchApi diiaBranchApi;

    public BranchServiceImpl(DiiaBranchApi diiaBranchApi) {
        this.diiaBranchApi = diiaBranchApi;
    }

    @Override
    public BranchList getBranches(Long skip, Long limit) {
        return diiaBranchApi.getBranches(skip, limit);
    }

    @Override
    public Branch getBranch(String branchId) {
        return diiaBranchApi.getBranchById(branchId);
    }

    @Override
    public void deleteBranch(String branchId) {
        diiaBranchApi.deleteBranchById(branchId);
    }

    @Override
    public String createBranch(Branch request) {
        Id branchId = diiaBranchApi.createBranch(request);
        return branchId.getId();
    }

    @Override
    public Branch updateBranch(Branch request) {
        return diiaBranchApi.updateBranch(request);
    }
}
