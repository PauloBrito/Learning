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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	Object pagina;

	@Autowired
	private Titulos titulos;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		modelView.addObject(new Titulo());

		pagina = "CadastroTitulo";
		modelView.addObject("pagina", pagina);
		return modelView;
	}

	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.POST)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

		titulos.deleteById(id);

		pagina = "PesquisarTitulo";
		attributes.addFlashAttribute("pagina", pagina);
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}

	@RequestMapping
	public ModelAndView pesquisar() {
		ModelAndView modelView = new ModelAndView("PesquisarTitulo");

		List<Titulo> todosTitulos = titulos.findAll();
		pagina = "PesquisarTitulo";
		modelView.addObject("pagina", pagina);
		modelView.addObject("todosTitulos", todosTitulos);
		return modelView;
	}

	@RequestMapping("/editar/{id}")
	public ModelAndView edidar(@PathVariable("id") Titulo titulo) {
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		pagina = "EditarTitulo";
		modelView.addObject("pagina", pagina);
		modelView.addObject(titulo);
		return modelView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		ModelAndView modelView = new ModelAndView("CadastroTitulo");
		pagina = "CadastroTitulo";
		if (errors.hasErrors()) {
			modelView.addObject("pagina", pagina);
			return modelView;
		}
		try {
			
			titulos.save(titulo);
			titulo = new Titulo();
			modelView = new ModelAndView("CadastroTitulo");
			modelView.addObject(titulo);
			modelView.addObject("pagina", pagina);
			modelView.addObject("mensagem", "Salvo com sucesso!");
		} catch (DataIntegrityViolationException e) {
			errors.rejectValue("dataVencimento",null, "Formato de data inválido");
		}

		return modelView;
	}

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> StatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}

}
