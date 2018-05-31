package com.parduota.parduota;

import java.util.Scanner;

class GenerateJava {


    private static final String data = "title\n" +
            "description\n" +
            "price\n" +
            "location\n" +
            "country\n" +
            "quantity\n" +
            "media\n" +
            "condition\n" +
            "weight\n" +
            "length\n" +
            "width\n" +
            "height\n" +
            "shipping_type\n" +
            "shipping_service\n" +
            "shipping_service_cost\n" +
            "shipping_service_add_cost\n" +
            "sell_for_charity\n" +
            "status\n" +
            "shipping_type_custom";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(data);

        while (scanner.hasNext()) {

            System.out.println("  params.put(" + scanner.next().toUpperCase() + ", Arrays.asList());\n" +
                    "          ");


        }

        while (scanner.hasNext()) {

            System.out.println("String " + scanner.next().toUpperCase() + " = " + scanner.next());


        }

        scanner.close();

    }
}
