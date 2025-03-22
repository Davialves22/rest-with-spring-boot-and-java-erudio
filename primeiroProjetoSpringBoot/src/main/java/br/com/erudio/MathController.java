package br.com.erudio;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exceptions.UnsupportedMathOperationException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/math")
public class MathController {

    private static final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) {

        return calculate(numberOne, numberTwo, "sum");

    }

    @RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sub(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        return calculate(numberOne, numberTwo, "sub");

    }

    @RequestMapping(value = "/mult/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double mult(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        return calculate(numberOne, numberTwo, "mult");

    }

    @RequestMapping(value = "/div/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double div(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) {
        if (convertToDouble(numberTwo) == 0) {
            throw new UnsupportedMathOperationException("Não é possível dividir por zero!");
        }

        return calculate(numberOne, numberTwo, "div");

    }

    @RequestMapping(value = "/avg/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double avg(
            @PathVariable(value = "numberOne") String numberOne,
            @PathVariable(value = "numberTwo") String numberTwo) {
        return calculate(numberOne, numberTwo, "avg");

    }

    private Double calculate(String numberOne, String numberTwo, String operation) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Por favor, insira um número valído!");
        }
        Double num1 = convertToDouble(numberOne);
        Double num2 = convertToDouble(numberTwo);

        return switch (operation) {
            case "sum" -> num1 + num2;
            case "sub" -> num1 - num2;
            case "div" -> num1 / num2;
            case "mult" -> num1 * num2;
            case "avg" -> (num1 + num2) / 2;
            default -> throw new UnsupportedMathOperationException("Operação não suportada!");
        };
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null)
            return 0D;

        String number = strNumber.replaceAll(",", ".");

        if (isNumeric(number))
            return Double.parseDouble(number);
        return 0D;
    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null)
            return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
