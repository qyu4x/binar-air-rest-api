package com.binarair.binarairrestapi.service.impl;

import com.binarair.binarairrestapi.service.QRCodeGeneratorService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;



    @Service
    public class QRCodeGeneratorServiceImpl implements QRCodeGeneratorService {

        @Override
        public void genereateQRCodeImage(String text, int with, int height, String filePath) throws WriterException, IOException {

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, with, height);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);


        }

        @Override
        public byte[] getQRCodeImage(String text, int with, int height) throws WriterException, IOException {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, with, height);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig config = new MatrixToImageConfig();

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, config);
            byte[] pngData = pngOutputStream.toByteArray();
            return pngData;
        }
    }

