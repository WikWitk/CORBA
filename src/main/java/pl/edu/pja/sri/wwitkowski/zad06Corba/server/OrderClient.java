package pl.edu.pja.sri.wwitkowski.zad06Corba.server;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.PolicyListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import pl.edu.pja.sri.wwitkowski.zad06_corba_order.Order;
import pl.edu.pja.sri.wwitkowski.zad06_corba_order.OrderHolder;
import pl.edu.pja.sri.wwitkowski.zad06_corba_order.OrderSystem;
import pl.edu.pja.sri.wwitkowski.zad06_corba_order.OrderSystemHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class OrderClient {
    public static void main(String[] args) throws Exception {
        // Inicjalizacja ORB
        Properties p = new Properties();

        p.put("ORBInitRef.NameService", "corbaloc::localhost:2809/NameService");

        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, p);

        // Pobieranie referencji do usługi z usługi nazwowej
        org.omg.CORBA.Object objRef = readRefFromFile(orb, "OrderSystem");

        OrderSystem orderSystem = OrderSystemHelper.narrow(objRef);

        // Wywołanie metod biznesowych
        createOrder(orderSystem);
        Order order = getOrderById(orderSystem, 1);
        removeOrder(orderSystem, 1);

        // Wyświetlenie wyników
        System.out.println("Nowe zamówienie zostało utworzone.");
        System.out.println("Dane zamówienia:");
        System.out.println("Order ID: " + order.orderId);
        System.out.println("Client Name: " + order.clientName);
        System.out.println("Products: " + order.products);
        System.out.println("Total Amount: " + order.totalAmount);

    }


    // Metoda biznesowa do utworzenia nowego zamówienia
    private static void createOrder(OrderSystem orderSystem) {
        Order order1 = new Order();
        order1.orderId = 1;
        order1.clientName = "John Doe";
        order1.products = new String[]{"Product A, Product B"};
        order1.totalAmount = 100.0;

        orderSystem.addOrder(order1);
    }

    // Metoda biznesowa do pobrania zamówienia o określonym ID
    private static Order getOrderById(OrderSystem orderSystem, int orderId) {
        return orderSystem.getOrderByID(orderId);
    }

    // Metoda biznesowa do usunięcia zamówienia o określonym ID
    private static void removeOrder(OrderSystem orderSystem, int orderId) {
        orderSystem.removeOrder( orderId);
    }

    private static org.omg.CORBA.Object readRefFromFile(ORB orb, String refName) throws Exception {
        org.omg.CORBA.Object o = orb.resolve_initial_references("NameService");

        //rzutowanie obiektu CORBA na obiekt Java
        NamingContextExt rootContext = NamingContextExtHelper.narrow(o);

        //tworzenie komponentu nazwowegi
        NameComponent nc = new NameComponent(refName, "");
        // tworzenie ścieżki nazwowej
        NameComponent path[] = {nc};
        // pobranie obiektu z usługi nazwowej
        return rootContext.resolve(path);

    }
}
