package com.algaworks.cobranca.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Titulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Descrição é obrigatória")
	@Size(max = 60 , message = "Descrição não pode conter mais de 60 caracteres")
	private String descricao;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@DateTimeFormat(pattern = "yyyy/MM/dd")
	// @JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Data de vencimento é obrigatória")
	private Date dataVencimento;

	
	@Enumerated(EnumType.STRING)
	@NotNull
	private StatusTitulo status;

	
	@NumberFormat(pattern = "##,#00.00")
	@NotNull(message = "Valor é obrigatório")
	@DecimalMax(value = "999999999.99", message = "Valor não pode ser mais que 1 bilhão")
	@DecimalMin(value = "0.99", message = "Valor não pode ser menor que 1 real")
	private BigDecimal valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public StatusTitulo getStatus() {
		return status;
	}

	public void setStatus(StatusTitulo status) {
		this.status = status;
	}

	public BigDecimal getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return "Titulo [id=" + id + ", descricao=" + descricao + ", dataVencimento=" + dataVencimento + ", status="
				+ status + ", valor=" + valor + "]";
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isPendente() {
		return StatusTitulo.PENDENTE.equals(this.status);
	}
	
	public boolean isRecebido() {
		return StatusTitulo.RECEBIDO.equals(this.status);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Titulo other = (Titulo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
