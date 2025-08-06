package kh.com.csx.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class Util {
    public static String toSlug(String input) {
        return input.toLowerCase()                                            // convert to lowercase
                .replaceAll("[^a-z0-9\\s-]", "")           // remove non-alphanumeric characters
                .replaceAll("\\s+", "-")                   // replace spaces with dashes
                .replaceAll("-+", "-")                     // collapse multiple dashes
                .replaceAll("^-|-$", "");                  // remove leading/trailing dashes
    }

    public static String slugify(String fileName) {
        // Convert to lowercase
        String slug = fileName.toLowerCase();
        // Replace spaces with hyphens
        slug = slug.replaceAll("\\s+", "-");
        // Remove any character that is not alphanumeric, hyphen, or dot
        slug = slug.replaceAll("[^a-z0-9\\-.]", "");
        // Handle consecutive hyphens
        slug = slug.replaceAll("-+", "-");
        // Remove leading or trailing hyphens
        return slug.replaceAll("^-|-$", "");
    }

    public static String getUniqueFileName(String boardName, String name, String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        String baseName = FilenameUtils.getBaseName(fileName);
        return boardName + "_" + name + "_" + baseName + "_" + UUID.randomUUID() + "." + extension;
    }

    public static String getMimeType(String fileName) {
        String fileNameLowerCase = fileName.toLowerCase();

        if (fileNameLowerCase.endsWith("gif")) {
            return "image/gif";
        } else if (fileNameLowerCase.endsWith("png")) {
            return "image/png";
        } else if (fileNameLowerCase.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileNameLowerCase.endsWith("jpg")) {
            return "image/jpg";
        } else if (fileNameLowerCase.endsWith("wmv")) {
            return "video/x-ms-video";
        } else if (fileNameLowerCase.endsWith("mp4")) {
            return "video/mp4";
        } else if (fileNameLowerCase.endsWith("pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream";
        }
    }

    public static Pageable getPageable(Integer page, Integer limit) {
        return PageRequest.of(getCurrentPage(page), limit);
    }

    public static Integer getCurrentPage(Integer page) {
        return Math.max(page - 1, 0);
    }
}
