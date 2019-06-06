package com.sharePhoto.auth.utils.generateAvatar;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.sharePhoto.auth.service.fastdfsService.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("ava")
public class GenerateAvatar {

    @Value("${fastdfs.base.url}")
    private String FASTDFS_BASE_URL;

    @Resource
    private StorageService storageService;

    private enum sizes {
        SMALL("_s", 30), MEDIUM("_m", 100), LARGE("_l", 200);
        // 成员变量
        private String suffix;
        private int size;
        // 构造方法 ,赋值给成员变量
        sizes(String suffix, int size) {
            this.suffix = suffix;
            this.size = size;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public List<String> generateAvatar(String username) throws IOException {
        Identicon identicon = new Identicon();

        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(username, Charsets.UTF_8);
        String md5 = hasher.hash().toString();

        List<String> filenames = new ArrayList<>();
        for (sizes size : sizes.values()){

            BufferedImage image = identicon.create(md5, size.getSize());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpg", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = baos.toByteArray();
            String uploadUrl = storageService.upload(bytes, "jpg");
            String url = FASTDFS_BASE_URL + uploadUrl;
            filenames.add(url);
        }

        return filenames;



    }


}
