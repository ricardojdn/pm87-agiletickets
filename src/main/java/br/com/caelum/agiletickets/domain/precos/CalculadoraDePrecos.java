package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = sessao.getPreco();
		TipoDeEspetaculo tipoEspetaculo = sessao.getEspetaculo().getTipo();
		if (tipoEspetaculo.equals(TipoDeEspetaculo.CINEMA) || tipoEspetaculo.equals(TipoDeEspetaculo.SHOW)) {
				preco = retornaPrecoPorTotalIngresso(sessao, 0.05, 0.10);
		} else if (tipoEspetaculo.equals(TipoDeEspetaculo.BALLET) || tipoEspetaculo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = retornaPrecoPorTotalIngresso(sessao, 0.50, 0.20);
			if (sessao.getDuracaoEmMinutos() > 60) {
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	
	private static BigDecimal retornaPrecoPorTotalIngresso(Sessao sessao, Double taxaTotal, Double taxaPreco) {
		BigDecimal preco = sessao.getPreco();
		if ((sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue() <= taxaTotal) {
			preco = sessao.getPreco().add(
					sessao.getPreco().multiply(BigDecimal.valueOf(taxaPreco)));
		}
		return preco;
	}
}