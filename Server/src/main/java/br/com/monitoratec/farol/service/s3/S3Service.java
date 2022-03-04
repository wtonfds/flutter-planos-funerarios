package br.com.monitoratec.farol.service.s3;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class S3Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    private final AmazonS3 s3Client;
    private final String bucketName;

    public S3Service(AmazonS3 s3Client, @Value("${aws.s3-bucket}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public String uploadPdf(byte[] pdfData) {
        final String fileName = UUID.randomUUID().toString() + ".pdf";
        LOGGER.info("Started uploading file {} to s3", fileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(MediaType.APPLICATION_PDF_VALUE);
        metadata.setContentLength(pdfData.length);

        try (InputStream stream = new ByteArrayInputStream(pdfData)) {
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, stream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(request);

            final String contractUrl = s3Client.getUrl(bucketName, fileName).toString();
            LOGGER.info("Finished uploading file {} to s3 with url {}", fileName, contractUrl);

            return contractUrl;
        } catch (SdkClientException | IOException e) {
            LOGGER.error("Error uploading file " + fileName, e);
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.ERROR_UPLOADING_CONTRACT);
        }
    }

    public String uploadImage(byte[] imageBytes) {
        final String fileName = RandomStringUtils.random(15, true, true) + ".jpeg";
        LOGGER.info("Started uploading image {} to s3", fileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        metadata.setContentLength(imageBytes.length);

        try (InputStream stream = new ByteArrayInputStream(imageBytes)) {
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, stream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(request);

            String imageUrl = s3Client.getUrl(bucketName, fileName).toString();
            s3Client.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
            LOGGER.info("Finished uploading image {} to s3 with url {}", fileName, imageUrl);

            return imageUrl;
        } catch (Exception e) {
            LOGGER.error("Error uploading image " + fileName, e);
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.AmazonS3.UNABLE_TO_SEND_IMAGE);
        }
    }

    public String uploadXml(File file) {
        final String fileName = file.getName();
        LOGGER.info("Started uploading xml nfs-e {} to s3", fileName);

        // Converting file to byte array
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.AmazonS3.UNABLE_TO_GET_BYTES);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(MediaType.APPLICATION_XML_VALUE);
        metadata.setContentLength(imageBytes.length);

        try (InputStream stream = new ByteArrayInputStream(imageBytes)) {
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, stream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(request);

            String xmlUrl = s3Client.getUrl(bucketName, fileName).toString();
            s3Client.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
            LOGGER.info("Finished uploading xml nfs-e {} to s3 with url {}", fileName, xmlUrl);

            return xmlUrl;
        } catch (Exception e) {
            LOGGER.error("Error uploading xml nfs-e " + fileName, e);
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.AmazonS3.UNABLE_TO_SEND_XML);
        }
    }
}
