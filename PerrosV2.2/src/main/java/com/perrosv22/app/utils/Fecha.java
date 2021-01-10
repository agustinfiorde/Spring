package com.perrosv22.app.utils;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Fecha  
{
	public static SimpleDateFormat FECHA_GUIONES = new SimpleDateFormat("dd-MM-yyyy");

	public static SimpleDateFormat FECHA = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es"));
	public static SimpleDateFormat FECHA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("es"));
	public static SimpleDateFormat FECHA_CALENDAR = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("es"));

	public static SimpleDateFormat FECHA_DIA = new SimpleDateFormat("EEEE dd", Locale.forLanguageTag("es"));

	private static final Fecha instance = new Fecha();

	public static NumberFormat FORMATO_INGLES;
	public static NumberFormat FORMATO_ESPANOL;

	static {
		FORMATO_INGLES = NumberFormat.getInstance(Locale.US);
		FORMATO_INGLES.setMinimumFractionDigits(2);
		FORMATO_INGLES.setMaximumFractionDigits(2);
		FORMATO_INGLES.setGroupingUsed(false);

		FORMATO_ESPANOL = NumberFormat.getInstance(Locale.US);
		FORMATO_ESPANOL.setMinimumFractionDigits(2);
		FORMATO_ESPANOL.setMaximumFractionDigits(2);
		FORMATO_ESPANOL.setGroupingUsed(true);
	}

	public static Fecha getInstance() {
		return instance;
	}
	
	public static Date parseFechaGuiones(String f) {
		try {
			return FECHA_CALENDAR.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String formatFechaGuiones(Date f) {
		try {
			return FECHA_CALENDAR.format(f);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date parseFecha(String f) {
		try {
			return FECHA_CALENDAR.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parseHora(String f) {
		try {
			return FECHA_HORA.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatFecha(Date f) {
		try {
			return FECHA.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatHora(Date f) {
		try {
			return FECHA_HORA.format(f);
		} catch (Exception e) {
			return null;
		}
	}

//	public static Date primerDiaAnio() {
//		DateTime date = new DateTime().dayOfYear().withMinimumValue().withTimeAtStartOfDay();
//		return date.toDate();
//	}
//
//	public static Date ultimoDiaAnio() {
//		DateTime date = new DateTime().plusYears(1).dayOfYear().withMinimumValue().withTimeAtStartOfDay();
//		return date.toDate();
//	}
}
