function mostrar(){
	$("#buscadoractivo").removeClass('slideOutLeft animated');

	$("#buscadoractivo").css('display', 'block');
	$("#buscadoractivo").addClass('slideInLeft animated');
}

function ocultar(){
	$("#buscadoractivo").removeClass('slideInLeft animated');
	$("#buscadoractivo").addClass('slideOutLeft animated');
}