package br.com.john.prgweb.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.john.prgweb.dao.ArquivoDao;
import br.com.john.prgweb.domain.Arquivo;
 
@ManagedBean
public class FileDownloadView {
     
    private StreamedContent file;
     
    public FileDownloadView() throws FileNotFoundException {   
    	FacesContext context = FacesContext.getCurrentInstance();
    	String arqId = context.getExternalContext().getRequestParameterMap().get("arqCodigo");
    	ArquivoDao ad = new ArquivoDao();
    	Arquivo arquivo = ad.buscar(Long.parseLong(arqId));
    	File f = new File(arquivo.getCaminhoArquivo());
    	if(f.isFile()){
    		arquivo.setDownloads(arquivo.getDownloads()+1);
    		ad.merge(arquivo);
    		String tipo = FacesContext.getCurrentInstance().getExternalContext().getMimeType(f.getAbsolutePath());
    		String caminho = arquivo.getCaminhoArquivo();
    		String ext = caminho.substring(caminho.lastIndexOf('.'), caminho.length());
    		InputStream stream = new FileInputStream(f.getAbsolutePath());
    		file = new DefaultStreamedContent(stream, tipo, arquivo.getNome()+ext);
    	}
    	else{
    		Messages.addGlobalError("Arquivo não encontrado!");
    	}
    }
 
    public StreamedContent getFile() {
        return file;
    }
}