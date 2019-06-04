package com.sharePhoto.dataFaker.services.generateImage;

import com.sharePhoto.dataFaker.services.fastdfsService.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component("generator")
public class GenerateImage {
    @Value("${fastdfs.base.url}")
    private String FASTDFS_BASE_URL;

    @Autowired
    private StorageService storageService;

    public String generateImage(){

        BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, 800, 800);
        //g.setPaint(Color.BLUE);
        g.setColor(new Color((int)(128+Math.random()*(127)), (int)(128+Math.random()*(127)), (int)(128+Math.random()*(127))));
        g.fillRect(0, 0, 800,800);
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();

        String uploadUrl = storageService.upload(bytes, "jpg");
        String url = FASTDFS_BASE_URL + uploadUrl;
        return url;
    }


}