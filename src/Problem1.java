

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Problema: 
 *              1. Carregar o arquivo cte.xml localizado na pasta ./res/.
 *              2. Criar um objeto Cte (Conhecimento de Transporte).
 *              3. Preencher os campos, sabendo os seguintes caminhos na estrutura XML:
 *              
 *              - serie:   <cteProc><CTe><infCte><ide><serie>
 *              - chave:   <cteProc><CTe><infCte Id=>
 *              - emissao: <cteProc><CTe><infCte><ide><dhEmi>
 *              - valor: <cteProc><CTe><infCte><vPrest><vTPrest>
 *
 */
public class Problem1 {

	
	/**
	 * Implementar metodo abaixo
	 */
	private static Cte loadCteFromXML(File file) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
       
        Cte objCte = new Cte();
        
		try {
			docBuilder = dbf.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			
			NodeList listaCTe = doc.getElementsByTagName("CTe");
			
			Integer serie;
			String chave;
			String emissao;
			double valor;
			
			// acessando InfCte
			for (int i=0;i<listaCTe.getLength();i++) {
				NodeList listaInfCte = listaCTe.item(i).getChildNodes();
				
				Node noInfCte = listaInfCte.item(1); //local do InfCte
				//pegando id
				if (noInfCte.getNodeType() == Node.ELEMENT_NODE)
				{
					Element elementoInfCte = (Element) noInfCte;
					chave = elementoInfCte.getAttribute("Id");
					objCte.setChave(chave);
					
					//acessando ide
					NodeList listaNoIde = elementoInfCte.getChildNodes();
					for (int j = 0; j < listaNoIde.getLength(); j++) {
						Node noIde = listaNoIde.item(1); // local do ide
						if (noIde.getNodeType() == Node.ELEMENT_NODE) {
							Element elementoIde = (Element) noIde;
							//pegando serie e emissao
							serie = Integer.parseInt(elementoIde.getElementsByTagName("serie").item(0).getTextContent());
							emissao = elementoIde.getElementsByTagName("dhEmi").item(0).getTextContent(); 
							objCte.setSerie(serie);
							objCte.setEmissao(emissao);
							break; // quando achar os atributos
						}
					}
					
					NodeList listaNoVTPrest = elementoInfCte.getChildNodes();
					for (int k = 0; k < listaNoIde.getLength(); k++) {
						Node noIde = listaNoVTPrest.item(11); // local do vTPrest
						if (noIde.getNodeType() == Node.ELEMENT_NODE) {
							Element elementoIde = (Element) noIde;
							//pegando valor
							valor = Double.parseDouble(elementoIde.getElementsByTagName("vTPrest").item(0).getTextContent());
							objCte.setValor(valor);
							break; // quando achar os atributos
						}
					}
				}
			}	
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return objCte;
	}
	
	/**
	 * NÃO ALTERAR METODO EVAL
	 */
	
	private void eval() {
		Cte cte = loadCteFromXML(new File("./res/cte.xml"));
		
		boolean failed=false;
		if(cte != null && cte.getSerie() != null && cte.getSerie() == 4)
			System.out.println("[OK] campo \"serie\" com valor correto");
		else {
			System.out.println("[FAIL] Campo \"serie\" com valor incorreto. Valor esperado: 4");
			failed=true;
		}
		
		if(cte != null && cte.getChave() != null && cte.getChave().equals("CTe99999704012799900349570040000165560001225662"))
			System.out.println("[OK] campo \"chave\" com valor correto");
		else {
			System.out.println("[FAIL] Campo \"chave\" com valor incorreto. Valor esperado: CTe99999704012799900349570040000165560001225662");
			failed=true;
		}
		
		if(cte != null && cte.getEmissao() != null && cte.getEmissao().equals("2011-07-05T14:42:29"))
			System.out.println("[OK] campo \"emissao\" com valor correto");
		else {
			System.out.println("[FAIL] Campo \"emissao\" com valor incorreto. Valor esperado: 2011-07-05T14:42:29");
			failed=true;
		}
		
		if(cte != null && cte.getValor() != null && cte.getValor() == 50.13)
			System.out.println("[OK] campo \"valor\" com valor correto");
		else {
			System.out.println("[FAIL] Campo \"valor\" com valor incorreto. Valor esperado: 50.13");
			failed=true;
		}
		
		if(!failed)
			System.out.println("PROBLEM SOLVED!");
	}

	/**
	 * NAO ALTERAR METODO MAIN
	 */
	public static void main(String[] args) { // add static na funcao
		new Problem1().eval();
		System.exit(0);
	}
	
	
	
}
