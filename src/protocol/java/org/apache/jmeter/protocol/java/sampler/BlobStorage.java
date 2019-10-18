package protocol.java.org.apache.jmeter.protocol.java.sampler;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.io.Serializable;

public class BlobStorage extends AbstractJavaSamplerClient implements Serializable {
    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("container", "");
        defaultParameters.addArgument("account_name", "");
        defaultParameters.addArgument("account_key", "");
        defaultParameters.addArgument("file_name", "");
        return defaultParameters;
    }

    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        String containerName = javaSamplerContext.getParameter("container");
        String accountName = javaSamplerContext.getParameter("account_name");
        String accountKey = javaSamplerContext.getParameter("account_key");
        String fileName = javaSamplerContext.getParameter("file_name");

        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient;
        CloudBlobContainer container;

        SampleResult result = new SampleResult();
        result.sampleStart(); // start stopwatch
        try {
            storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=https;" +
                    "AccountName="+accountName+";" +
                    "AccountKey="+accountKey
            );
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(fileName);

            String blobContents = blob.downloadText();

            result.sampleEnd();
            result.setSuccessful(true);
            result.setResponseData(blobContents, "UTF-8");
            result.setResponseMessageOK();
        } catch (Exception e) {
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage("Exception:" + e);

            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(stringWriter));
            result.setResponseData(stringWriter.toString(), "UTF-8");
            result.setDataType(org.apache.jmeter.samplers.SampleResult.TEXT);
            result.setResponseCode("500");
        }
        return result;
    }
}
