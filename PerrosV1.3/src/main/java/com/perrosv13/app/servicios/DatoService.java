package com.perrosv13.app.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perrosv13.app.entidades.Dato;
import com.perrosv13.app.repositorios.DatoRepository;

@Service
public class DatoService {

	@Autowired
	private DatoRepository datoRepository;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Dato crear(Dato entidad) {

		entidad.setCantidad(entidad.getCantidad() + 10);
		entidad.setCreado(new Date());

		return datoRepository.save(entidad);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Dato editar(Dato entidad) {

		entidad.setCantidad(entidad.getCantidad() + 10);
		entidad.setEditado(new Date());

		return datoRepository.save(entidad);
	}

	@Transactional(readOnly = true)
	public Dato obtener() {

		List<Dato> lista = datoRepository.findAll();

		if (lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	/*
	 * https://www.baeldung.com/spring-scheduled-tasks?utm_source=feedburner&
	 * utm_medium=feed&utm_campaign=Feed%3A+Baeldung+(baeldung)
	 * 
	 * https://www.baeldung.com/cron-expressions
	 * 
	 * https://riptutorial.com/es/spring/example/21209/cron-expresion
	 */
//	@Scheduled(fixedRate = 10000)
	@Scheduled(cron="*/60 * * * * *")//cada 60 segundos
	public void resolver() {

		try {
			Dato entidad = datoRepository.findAll().get(0);

			if (entidad != null) {
				editar(entidad);
			}

		} catch (Exception e) {
			crear(new Dato());
		}

	}

}
