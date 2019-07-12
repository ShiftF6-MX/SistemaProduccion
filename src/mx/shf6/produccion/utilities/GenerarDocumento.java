package mx.shf6.produccion.utilities;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.shf6.produccion.model.Cotizacion;
import mx.shf6.produccion.model.DetalleCardex;
import mx.shf6.produccion.model.DetalleComponente;
import mx.shf6.produccion.model.DetalleCotizacion;
import mx.shf6.produccion.model.dao.DetalleCotizacionDAO;
import mx.shf6.produccion.view.DialogoMovimientoInventario;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GenerarDocumento {
	//PROPIEDADES
	private static List<DetalleCotizacion> datosTabla;

	public static void generaCotizacion(Connection connection, Cotizacion cotizacion) {
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/COTIZACION.jasper");
			datosTabla = DetalleCotizacionDAO.toList(DetalleCotizacionDAO.readCotizacionDetalle(connection, cotizacion.getSysPK()));
			JRBeanCollectionDataSource itemsTabla = new JRBeanCollectionDataSource(datosTabla);
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("Fecha", cotizacion.getFecha());
			parameters.put("Referencia", cotizacion.getReferencia());
			parameters.put("Cliente", cotizacion.getCliente(connection).getNombre());
			String domicilio = cotizacion.getCliente(connection).getDomicilio(connection).showDomicilioCompleto();
			if (domicilio != null)
				parameters.put("Domicilio", domicilio);
			else
				parameters.put("Domicilio", "");
			parameters.put("Solicitante", cotizacion.getSolicitante());
			parameters.put("AreaDepartamento", cotizacion.getAreaDepartamento());
			parameters.put("TelefonoFax", cotizacion.getTelefonoFax());
			parameters.put("Email", cotizacion.getEmail());
			parameters.put("TipoServicio", cotizacion.getTipoServicio());
			parameters.put("TablaReporte", itemsTabla);
			parameters.put("FechaEntrega", cotizacion.getFechaEntrega());
			parameters.put("CondicionEmbarque", cotizacion.getCondicionEmbarque());
			parameters.put("CondicionPago", cotizacion.getCondicionPago());
			parameters.put("Moneda", cotizacion.getDescripcionMoneda());
			parameters.put("TipoCambio", cotizacion.getTipoCambio());
			parameters.put("Observaciones", cotizacion.getObservaciones());
			parameters.put("Vigencia", cotizacion.getVigencia());
			parameters.put("JefePlaneacion", "OMAR GARRIDO ISLAS");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
			jasperView.setTitle("Cotizacion: " + cotizacion.getReferencia());
		} catch (JRException jre) {
			//Notificacion.dialogoException(jre);
			System.out.println(jre);
		}//TRY/CATH
	}//END METHOD


	public static void generaValeMovimientoInventario(Connection connection, ArrayList <DetalleCardex> listaDetalleCardex, int tipoMovimiento) {
		String titulo = "";
		String referencia = "";
		try {
			if(tipoMovimiento == DialogoMovimientoInventario.ENTRADA)
				titulo = "VALE ENTRADA";
			else if(tipoMovimiento == DialogoMovimientoInventario.SALIDA )
				titulo = "VALE SALIDA";

			for(DetalleCardex detalleCardex : listaDetalleCardex ){
				referencia = detalleCardex.getCardex(connection).getReferencia();
			}

			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("TipoMovimiento", tipoMovimiento );
			parameters.put("Titulo", titulo);
			parameters.put("Referencia", referencia);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/ValeMovimientoInventario.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(listaDetalleCardex));
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
		} catch (JRException jre) {
			Notificacion.dialogoException(jre);
			System.out.println(jre);
		}//TRY/CATH
	}//END METHOD

	public static void generaListaMateriales(Connection connection, ArrayList <DetalleComponente> listaSubEnsambles, ArrayList <DetalleComponente> listaEnsambles, ArrayList <DetalleComponente> listaPartePrimaria, int tamañoArrayPartesPrimarias) {
		int i= 0;
		ArrayList<DetalleComponente> listaPartesPrimarias = new ArrayList<DetalleComponente>();
		for(DetalleComponente detalleComponente : listaPartePrimaria ){
			i++;
			if(i<= tamañoArrayPartesPrimarias)
				listaPartesPrimarias.add(detalleComponente);
		}//FIN FOR
		JRBeanCollectionDataSource itemsTablaSubEnsambles = new JRBeanCollectionDataSource(listaSubEnsambles);
		JRBeanCollectionDataSource itemsTablaEnsambles = new JRBeanCollectionDataSource(listaEnsambles);
		try {

			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("CantidadPartesPrimarias", tamañoArrayPartesPrimarias);
			parameters.put("DatosTablaSubEnsambles", itemsTablaSubEnsambles );
			parameters.put("DatosTablaEnsambles", itemsTablaEnsambles );

			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("resources/ListaMateriales.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(listaPartesPrimarias));
			JasperViewer jasperView = new JasperViewer(jasperPrint, false);
			jasperView.setVisible(true);
		} catch (JRException jre) {
			Notificacion.dialogoException(jre);
			System.out.println(jre);
		}//TRY/CATH
	}//END METHOD


}
