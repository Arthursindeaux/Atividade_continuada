package br.edu.cs.poo.ac.bolsa.util;

public class ValidadorCpfCnpj {

    public static ResultadoValidacao validarCpf(String cpf) {
        if (cpf == null || cpf.isEmpty())
            return ResultadoValidacao.NAO_INFORMADO;

        String nums = cpf.replaceAll("[^0-9]", "");

        if (nums.length() != 11)
            return ResultadoValidacao.FORMATO_INVALIDO;

        if (nums.matches("(\\d)\\1{10}"))
            return ResultadoValidacao.FORMATO_INVALIDO;

        if (!validarDigitosCpf(nums))
            return ResultadoValidacao.DV_INVALIDO;

        return null;
    }

    public static ResultadoValidacao validarCnpj(String cnpj) {
        if (cnpj == null || cnpj.isEmpty())
            return ResultadoValidacao.NAO_INFORMADO;

        String nums = cnpj.replaceAll("[^0-9]", "");

        if (nums.length() != 14)
            return ResultadoValidacao.FORMATO_INVALIDO;

        if (nums.matches("(\\d)\\1{13}"))
            return ResultadoValidacao.FORMATO_INVALIDO;

        if (!validarDigitosCnpj(nums))
            return ResultadoValidacao.DV_INVALIDO;

        return null;
    }

    private static boolean validarDigitosCpf(String nums) {
        int soma = 0;
        for (int i = 0; i < 9; i++)
            soma += Character.getNumericValue(nums.charAt(i)) * (10 - i);
        int d1 = 11 - (soma % 11);
        if (d1 >= 10) d1 = 0;

        soma = 0;
        for (int i = 0; i < 10; i++)
            soma += Character.getNumericValue(nums.charAt(i)) * (11 - i);
        int d2 = 11 - (soma % 11);
        if (d2 >= 10) d2 = 0;

        return d1 == Character.getNumericValue(nums.charAt(9))
            && d2 == Character.getNumericValue(nums.charAt(10));
    }

    private static boolean validarDigitosCnpj(String nums) {
        int[] peso1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int soma = 0;
        for (int i = 0; i < 12; i++)
            soma += Character.getNumericValue(nums.charAt(i)) * peso1[i];
        int d1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        int[] peso2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};
        soma = 0;
        for (int i = 0; i < 13; i++)
            soma += Character.getNumericValue(nums.charAt(i)) * peso2[i];
        int d2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        return d1 == Character.getNumericValue(nums.charAt(12))
            && d2 == Character.getNumericValue(nums.charAt(13));
    }
}