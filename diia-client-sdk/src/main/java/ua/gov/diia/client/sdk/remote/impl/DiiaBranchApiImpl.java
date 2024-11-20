package ua.gov.diia.client.sdk.remote.impl;

import okhttp3.HttpUrl;
import ua.gov.diia.client.sdk.exception.DiiaClientException;
import ua.gov.diia.client.sdk.remote.DiiaBranchApi;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.model.Branch;
import ua.gov.diia.client.sdk.remote.model.BranchList;
import ua.gov.diia.client.sdk.remote.model.Id;

import java.io.IOException;
import java.util.Objects;

public class DiiaBranchApiImpl implements DiiaBranchApi {
    private final String baseDiiaUrl;
    private final HttpMethodExecutor httpMethodExecutor;

    public DiiaBranchApiImpl(String baseDiiaUrl, HttpMethodExecutor httpMethodExecutor) {
        this.baseDiiaUrl = baseDiiaUrl;
        this.httpMethodExecutor = httpMethodExecutor;
    }

    /*
        curl -X POST "{diia_host}/api/v2/acquirers/branch"
        -H "accept: application/json"
        -H "Authorization: Bearer {session_token}"
        -H "Content-Type: application/json"
        -d "{\"customFullName\":\"Повна назва запитувача документа\", \"customFullAddress\":\"Повна адреса відділення\",
        \"name\":\"Назва відділення\", \"email\":\"test@email.com\", \"region\":\"Київська обл.\",
        \"district\":\"Києво-Святошинський р-н\", \"location\":\"м. Вишневе\", \"street\":\"вул. Київська\",
        \"house\":\"2г\", \"deliveryTypes\": [\"api\"], \"offerRequestType\": \"dynamic\",
        \"scopes\":{\"sharing\":[\"passport\",\"internal passport\",\"foreign-passport\"], \"identification\":[],
        \"documentIdentification\":[\"internal-passport\",\"foreign passport\"]}}"
     */
    @Override
    public Id createBranch(Branch request) {
        try {
            String url = String.format("%s/api/v2/acquirers/branch", baseDiiaUrl);
            return httpMethodExecutor.doPost(url, request, Id.class);
        } catch (IOException e) {
            throw new DiiaClientException("Branch creation error", e);
        }
    }

    /*
        curl -X GET "{diia_host}/api/v2/acquirers/branch/{branch_id}"
        -H "accept: application/json"
        -H "Authorization: Bearer {session_token}"
    */
    @Override
    public Branch getBranchById(String branchId) {
        try {
            String url = String.format("%s/api/v2/acquirers/branch/%s", baseDiiaUrl, branchId);
            return httpMethodExecutor.doGet(url, Branch.class);
        } catch (IOException e) {
            throw new DiiaClientException("Get branch error", e);
        }
    }

    /*
        curl -X DELETE "{diia_host}/api/v2/acquirers/branch/{branch_id}"
        -H "Accept: *//*"
        -H "Authorization: Bearer {session_token}"
        -H "Content-Type: application/json"
    */
    @Override
    public void deleteBranchById(String branchId) {
        try {
            String url = String.format("%s/api/v2/acquirers/branch/%s", baseDiiaUrl, branchId);
            httpMethodExecutor.doDelete(url);
        } catch (IOException e) {
            throw new DiiaClientException("Delete branch error", e);
        }
    }

    /*
        curl -X GET "{diia_host}/api/v2/acquirers/branches?skip=0&limit=2"
        -H "accept: application/json"
        -H "Authorization: Bearer {session_token}"
    */
    @Override
    public BranchList getBranches(Long skip, Long limit) {
        try {
            HttpUrl.Builder urlBuilder
                    = Objects.requireNonNull(
                    HttpUrl.parse(String.format("%s/api/v2/acquirers/branches", baseDiiaUrl))).newBuilder();
            if (skip != null) {
                urlBuilder.addQueryParameter("skip", skip.toString());
            }
            if (limit != null) {
                urlBuilder.addQueryParameter("limit", limit.toString());
            }
            String url = urlBuilder.build().toString();

            return httpMethodExecutor.doGet(url, BranchList.class);
        } catch (IOException e) {
            throw new DiiaClientException("Get branches error", e);
        }
    }

    /*
        curl -X PUT "{diia_host}/api/v2/acquirers/branch/{branch_id}"
        -H "Accept: application/json"
        -H "Authorization: Bearer {session_token}"
        -H "Content-Type: application/json"
        -d "{\"customFullName\":\"Повна назва запитувача документа\", \"customFullAddress\":\"Повна адреса відділення\",
        \"name\":\"Назва відділення\", \"email\":\"test@email.com\", \"region\":\"Київська обл.\",
        \"district\":\"Києво-Святошинський р-н\", \"location\":\"м. Вишневе\", \"street\":\"вул. Київська\",
        \"house\":\"2г\", \"deliveryTypes\": [\"api\"], \"offerRequestType\": \"dynamic\",
        \"scopes\":{\"sharing\":[\"passport\",\"internal passport\",\"foreign-passport\"], \"identification\":[],
        \"documentIdentification\":[\"internal-passport\",\"foreign passport\"]}}"
     */
    @Override
    public Branch updateBranch(Branch branch) {
        try {
            String url = String.format("%s/api/v2/acquirers/branch/%s", baseDiiaUrl, branch.getId().getId());
            httpMethodExecutor.doPut(url, branch, Id.class);
            return branch;
        } catch (IOException e) {
            throw new DiiaClientException("Branch creation error", e);
        }
    }
}
