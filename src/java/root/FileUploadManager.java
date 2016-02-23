/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author kirak
 */
@Path("/file")
public class FileUploadManager {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPdfFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {
        String UPLOAD_PATH = "C:/Users/kirak/Documents/NetBeansProjects/Project/web/files/";
        String retval = "files/" + fileMetaData.getFileName();
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            fileInputStream.read(bytes);
            if (bytes[0] == 0x4d && bytes[1] == 0x5a) {
                retval = "executable";
                return Response.status(403).entity(retval).build();
            }
            try (OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()))) {
                while ((read = fileInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
            }
        } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again !!");
        }
        return Response.status(200).entity(retval).build();
    }
}
