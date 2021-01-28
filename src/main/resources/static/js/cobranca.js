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

$(function() {

	$('.js-currency').maskMoney({ decimal: ',', thousands: '.', allowZero: true });
	$('.js-atualizar-status').on('click', function(event) {
		event.preventDefault();

		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');

		var responde = $.ajax({
			url: urlReceber,
			type: 'PUT'
		});
		responde.done(function(e) {
		var codigoTitulo = botaoReceber.data('id');
			$('[data-role=' + codigoTitulo + ']').html('<span class="badge rounded-pill bg-success">'+ e + '</span>');
			botaoReceber.hide();
		});
		responde.fail(function(e) {
			console.log(e);
			alert('Erro ao alterar status do título');
		});
	});
});