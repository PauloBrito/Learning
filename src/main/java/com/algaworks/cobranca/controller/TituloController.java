package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.service.TituloService;
import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	Object nomePagina;

	@Autowired
	private Titulos titulos;
	
	@Autowired
	private TituloService tituloService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		modelView.addObject(new Titulo());

		nomePagina = "Cadastro de Título";
		modelView.addObject("pagina", nomePagina);
		return modelView;
	}

	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.POST)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		System.out.println("Excluindo");

		tituloService.excluir(id);	

		nomePagina = "Excluir Título";
		attributes.addFlashAttribute("pagina", nomePagina);
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}

	@RequestMapping
	public ModelAndView pesquisar(@RequestParam(defaultValue = "") String descricao) {
		ModelAndView modelView = new ModelAndView("PesquisarTitulo");

		List<Titulo> todosTitulos = titulos.findByDescricaoContaining(descricao);
		nomePagina = "Pesquisa de Títulos";
		modelView.addObject("pagina", nomePagina);
		modelView.addObject("todosTitulos", todosTitulos);
		return modelView;
	}

	@RequestMapping("/editar/{id}")
	public ModelAndView edidar(@PathVariable("id") Titulo titulo) {
		System.out.println("Editando");
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		nomePagina = "Editar Título";
		modelView.addObject("pagina", nomePagina);
		modelView.addObject(titulo);
		return modelView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		System.out.println("Salvando");
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		nomePagina = "Cadastro de Título";
		if (errors.hasErrors()) {
			modelView.addObject("pagina", nomePagina);
			return modelView;
		}
		try {
			
			tituloService.salvar(titulo);
			titulo = new Titulo();
			modelView = new ModelAndView("CadastroTitulo");
			modelView.addObject(titulo);
			modelView.addObject("pagina", nomePagina);
			modelView.addObject("mensagem", "Salvo com sucesso!");
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento",null, e.getMessage());
		}

		return modelView;
	}

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> StatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
	
	@RequestMapping(value = "/{id}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long id) {
		//System.out.print("codigo>>>"+id);
		tituloService.alterarStatus(id);
		return StatusTitulo.RECEBIDO.getDescricao();
	}

}
