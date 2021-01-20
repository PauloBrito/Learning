$('#ModalExcluir').on('show.bs.modal', function(event) {

	var button = $(event.relatedTarget);

	var codigoTitulo = button.data('id');
	var descricaoTitulo = button.data('descricao');

	var modal = $(this)
	var form = modal.find('form');
	var action = form.data('url-base');

	form.attr('action', action + codigoTitulo);
	
	modal.find('.modal-body span').html('Deseja pagar o título <strong>' + 
	descricaoTitulo + '</strong> ?');

});