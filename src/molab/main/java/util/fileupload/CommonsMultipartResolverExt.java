package molab.main.java.util.fileupload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CommonsMultipartResolverExt extends CommonsMultipartResolver {
	
	@SuppressWarnings("unchecked")
	@Override
	public MultipartParsingResult parseRequest(HttpServletRequest request) {
		final String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		final HttpSession session = request.getSession();
		
		FileUploadListener uploadProgressListener = new FileUploadListener();
		fileUpload.setProgressListener(uploadProgressListener);
		session.setAttribute("uploadProgressListener", uploadProgressListener);

		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		} catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}
}
