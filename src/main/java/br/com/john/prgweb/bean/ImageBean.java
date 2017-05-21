package br.com.john.prgweb.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class ImageBean {

	public StreamedContent getImgArquivo() throws IOException{
		StreamedContent img = null;
		FacesContext context = FacesContext.getCurrentInstance();
		if(context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
			return new DefaultStreamedContent();
		}else{
			String imageId = context.getExternalContext().getRequestParameterMap().get("parCodigo");
			File f = new File("C:/Users/Usuário/Desktop/Trabalhos/Uploads/ImgArquivo"+imageId+".png");
			if(f.isFile()){
				Path path = Paths.get(f.getAbsolutePath());
				InputStream stream = Files.newInputStream(path);
				img = new DefaultStreamedContent(stream);
			}else{
				Path path = Paths.get("C:/Users/Usuário/Desktop/Trabalhos/Uploads/default.png");//imagem padrão
				InputStream stream = Files.newInputStream(path);
				img = new DefaultStreamedContent(stream);
			}
			return img;
		}
	}
	
	public StreamedContent getImgConta() throws IOException{
		StreamedContent img = null;
		FacesContext context = FacesContext.getCurrentInstance();
		if(context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE){
			return new DefaultStreamedContent();
		}else{
			String imageId = context.getExternalContext().getRequestParameterMap().get("parCodigo");
			File f = new File("C:/Users/Usuário/Desktop/Trabalhos/Uploads/ImgConta/"+imageId+".png");
			if(f.isFile()){
				Path path = Paths.get(f.getAbsolutePath());
				InputStream stream = Files.newInputStream(path);
				img = new DefaultStreamedContent(stream);
			}else{
				Path path = Paths.get("C:/Users/Usuário/Desktop/Trabalhos/Uploads/default.png");//imagem padrão
				InputStream stream = Files.newInputStream(path);
				img = new DefaultStreamedContent(stream);
			}
			return img;
		}
	}
	
}
