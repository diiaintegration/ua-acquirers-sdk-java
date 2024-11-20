package ua.gov.diia.client.example.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class QR {
    private static final Logger LOGGER = LoggerFactory.getLogger(QR.class);
    private static final String DEFAULT_IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";

    public static String generateQRFromUrl(String url) {
        String imgBase64 = DEFAULT_IMAGE;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", bao);
            imgBase64 = Base64.getEncoder().encodeToString(bao.toByteArray());
        } catch (IOException | WriterException e) {
            LOGGER.error("Can't generate QR code", e);
        }
        return "data:image/png;base64, " + imgBase64;
    }

    private QR() {
        throw new UnsupportedOperationException();
    }
}
