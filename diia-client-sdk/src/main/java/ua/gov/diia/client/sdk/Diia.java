package ua.gov.diia.client.sdk;

import okhttp3.OkHttpClient;
import ua.gov.diia.client.crypto.api.CryptoService;
import ua.gov.diia.client.sdk.model.*;
import ua.gov.diia.client.sdk.remote.HttpMethodExecutor;
import ua.gov.diia.client.sdk.remote.impl.DiiaBranchApiImpl;
import ua.gov.diia.client.sdk.remote.impl.DiiaOfferApiImpl;
import ua.gov.diia.client.sdk.remote.impl.DiiaSharingApiImpl;
import ua.gov.diia.client.sdk.remote.impl.DiiaValidationApiImpl;
import ua.gov.diia.client.sdk.remote.impl.DiiaIdApiImpl;
import ua.gov.diia.client.sdk.remote.model.*;
import ua.gov.diia.client.sdk.service.BranchService;
import ua.gov.diia.client.sdk.service.DocumentService;
import ua.gov.diia.client.sdk.service.OfferService;
import ua.gov.diia.client.sdk.service.SharingService;
import ua.gov.diia.client.sdk.service.ValidationService;
import ua.gov.diia.client.sdk.service.DiiaIdService;
import ua.gov.diia.client.sdk.service.impl.BranchServiceImpl;
import ua.gov.diia.client.sdk.service.impl.DocumentServiceImpl;
import ua.gov.diia.client.sdk.service.impl.OfferServiceImpl;
import ua.gov.diia.client.sdk.service.impl.SessionTokenServiceImpl;
import ua.gov.diia.client.sdk.service.impl.SharingServiceImpl;
import ua.gov.diia.client.sdk.service.impl.ValidationServiceImpl;
import ua.gov.diia.client.sdk.service.impl.DiiaIdServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Facade for all the Diia API
 */
public class Diia {
    protected DocumentService documentService;
    protected SharingService sharingService;
    protected BranchService branchService;
    protected OfferService offerService;
    protected ValidationService validationService;
    protected DiiaIdService diiaIdService;

    /**
     * Constructor
     *
     * @param acquirerToken A token used to identify the Partner
     * @param baseDiiaUrl   base URL to Diia REST API
     * @param httpClient    preconfigured http client
     * @param cryptoService preconfigured implementation of CryptoService interface
     */
    public Diia(String acquirerToken, String baseDiiaUrl, OkHttpClient httpClient, CryptoService cryptoService) {
        if(acquirerToken != null && baseDiiaUrl != null && httpClient != null) {
            SessionTokenServiceImpl sessionTokenService = new SessionTokenServiceImpl(acquirerToken, baseDiiaUrl, httpClient);
            HttpMethodExecutor httpMethodExecutor = new HttpMethodExecutor(sessionTokenService, httpClient);

            sharingService = new SharingServiceImpl(new DiiaSharingApiImpl(baseDiiaUrl, httpMethodExecutor));
            branchService = new BranchServiceImpl(new DiiaBranchApiImpl(baseDiiaUrl, httpMethodExecutor));
            offerService = new OfferServiceImpl(new DiiaOfferApiImpl(baseDiiaUrl, httpMethodExecutor));
            validationService = new ValidationServiceImpl(new DiiaValidationApiImpl(baseDiiaUrl, httpMethodExecutor));
            diiaIdService = new DiiaIdServiceImpl(cryptoService, new DiiaIdApiImpl(baseDiiaUrl, httpMethodExecutor));
        }

        if(cryptoService != null) {
            documentService = new DocumentServiceImpl(cryptoService);
        }
    }

    public Diia(String acquirerToken, String baseDiiaUrl, CryptoService cryptoService) {
        this(acquirerToken, baseDiiaUrl, new OkHttpClient(), cryptoService);
    }

    public Diia(String acquirerToken, String baseDiiaUrl, OkHttpClient httpClient) {
        this(acquirerToken, baseDiiaUrl, httpClient, null);
    }

    public Diia(String acquirerToken, String baseDiiaUrl) {
        this(acquirerToken, baseDiiaUrl, new OkHttpClient(), null);
    }

    public Diia(CryptoService cryptoService) {
        this(null, null, null, cryptoService);
    }

    /**
     * Get all branches
     *
     * @param skip  optional, count branches to return
     * @param limit optional, max count branches in response
     * @return list of branches
     */
    public BranchList getBranches(Long skip, Long limit) {
        return branchService.getBranches(skip, limit);
    }

    /**
     * Get branch by id
     *
     * @param branchId branch id
     * @return branch
     */
    public Branch getBranch(String branchId) {
        return branchService.getBranch(branchId);
    }

    /**
     * Delete branch
     *
     * @param branchId branch id
     */
    public void deleteBranch(String branchId) {
        branchService.deleteBranch(branchId);
    }

    /**
     * Create new branch
     *
     * @param branch new branch model
     * @return branch id
     */
    public String createBranch(Branch branch) {
        return branchService.createBranch(branch);
    }

    /**
     * Change existing branch
     *
     * @param branch updated branch model
     * @return updated branch
     */
    public Branch updateBranch(Branch branch) {
        return branchService.updateBranch(branch);
    }

    /**
     * Get all offers on the branch
     * There may be a lots of offers on one branch. So it's recommended to limiting requested offers count.
     *
     * @param branchId branch id
     * @param skip     (optional) number of offers to be skipped
     * @param limit    (optional) number of offers to be returned
     * @return list of offers
     */
    public OfferList getOffers(String branchId, Long skip, Long limit) {
        return offerService.getOffers(branchId, skip, limit);
    }

    /**
     * Create new offer on the branch
     *
     * @param branchId   branch id
     * @param name       offer name
     * @param returnLink (optional) link where the customer should be redirected after documents sharing confirmation
     * @param sharing    list of requested documents, see available document types here: {@link DocumentTypeConstant}
     * @param diiaId     list of allowed Diia Id operations. Available operations: auth, hashedFilesSigning
     * @return offer id
     */
    public String createOffer(String branchId, String name, String returnLink, List<String> sharing, List<String> diiaId) {
        return offerService.createOffer(branchId, name, returnLink, sharing, diiaId);
    }

    /**
     * Delete offer
     *
     * @param branchId branch id where the offer was created
     * @param offerId  offer id
     */
    public void deleteOffer(String branchId, String offerId) {
        offerService.deleteOffer(branchId, offerId);
    }

    /**
     * Validate document by barcode (on the back-side of document)
     *
     * @param branchId branch id
     * @param barcode  barcode
     * @return sign of document validity
     */
    public boolean validateDocumentByBarcode(String branchId, String barcode) {
        return validationService.validateDocumentByBarcode(branchId, barcode);
    }

    /**
     * Initiate document sharing procedure using document barcode
     *
     * @param branchId  branch id
     * @param barcode   barcode
     * @param requestId unique request id to identify document sharing action;
     *                  it will be sent in http-header with document pack
     * @return sign of successful request
     */
    public boolean requestDocumentByBarCode(String branchId, String barcode, String requestId) {
        return sharingService.requestDocumentByBarCode(branchId, barcode, requestId);
    }

    /**
     * Initiate document sharing procedure using document QR code
     *
     * @param branchId  branch id
     * @param qrcode    QR code data
     * @param requestId unique request id to identify document sharing action;
     *                  it will be sent in http-header with document pack
     * @return sign of successful request
     */
    public boolean requestDocumentByQRCode(String branchId, String qrcode, String requestId) {
        return sharingService.requestDocumentByQRCode(branchId, qrcode, requestId);
    }

    /**
     * Get deep link to start document sharing procedure using online scheme
     *
     * @param branchId  branch id
     * @param offerId   offer id
     * @param requestId unique request id to identify document sharing action;
     *                  it will be sent in http-header with document pack
     * @return URL, the deep link that should be opened on mobile device where Diia application is installed
     */
    public String getDeepLink(String branchId, String offerId, String requestId) {
        return sharingService.getDeepLink(branchId, offerId, requestId);
    }

    /**
     * Unpacking the documents pack received from Diia, check signatures and decipher documents
     *
     * @param headers         all http-headers from the request from Diia application
     * @param multipartBody   all parts of multipart-body from the request from Diia application
     * @param encodedJsonData encoded json meta data
     * @return a collection of received documents in PDF format and it's data in json format
     */
    public DocumentPackage decodeDocumentPackage(Map<String, String> headers, List<EncodedFile> multipartBody, String encodedJsonData) {
        return documentService.processDocumentPackage(headers, multipartBody, encodedJsonData);
    }

    /**
     * Check signatures and decipher the document received from Diia using barcode
     *
     * @param headers         all http-headers from the request from Diia application
     * @param multipartBody   part of multipart-body from the request from Diia application
     * @param encodedJsonData encoded json meta data
     * @return a collection of received documents in PDF format and it's data in json format
     */
    public DocumentPackage decodeDocumentReceivedByBarcode(Map<String, String> headers, EncodedFile multipartBody, String encodedJsonData) {
        return documentService.processDocumentReceivedByBarcode(headers, multipartBody, encodedJsonData);
    }

    /**
     * Get deep link to start file sign by Diia ID
     *
     * @param branchId      branch id
     * @param offerId       offer id
     * @param requestId     unique request id to identify file sign action;
     * @param filesToSign   list of files to be sign
     * @return URL, the deep link that should be opened on mobile device where Diia application is installed
     */
    public String getSignDeepLink(String branchId, String offerId, String requestId, List<FileData> filesToSign) {
        return diiaIdService.getSignDeepLink(branchId, offerId, requestId, filesToSign);
    }

    /**
     * Get deep link to authorize by Diia ID
     *
     * @param branchId      branch id
     * @param offerId       offer id
     * @param requestId     unique request id to identify diia ID auth action;
     * @return URL, the deep link and request ID with its hash
     */
    public AuthDeepLink getAuthDeepLink(String branchId, String offerId, String requestId) {
        return diiaIdService.getAuthDeepLink(branchId, offerId, requestId);
    }

    /**
     * Decode received Diia ID auth signature
     *
     * @param requestId    unique request id to identify diia ID auth action;
     * @param encodedData  encoded Diia ID auth signature
     * @return decoded signature
     */
    public Signature decodeSignature(String requestId, String encodedData) {
        return documentService.decodeSignature(requestId, encodedData);
    }

    /**
     * Decode received file hashes signed by Diia ID
     *
     * @param requestId    unique request id to identify diia ID auth action;
     * @param encodedData  encoded signed file hashes
     * @return decoded file hashes
     */
    public SignedFileHashes decodeSignedHashes(String requestId, String encodedData) {
        return documentService.decodeSignedHashes(requestId, encodedData);
    }
}