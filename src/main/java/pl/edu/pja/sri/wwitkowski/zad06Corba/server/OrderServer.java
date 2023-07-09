package pl.edu.pja.sri.wwitkowski.zad06Corba.server;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;


public class OrderServer {
    public static void main(String[] args) throws Exception{
            // Inicjalizacja ORB
//            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
        Properties p = new Properties();

        p.put("ORBInitRef.NameService", "corbaloc::localhost:2809/NameService");

        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, p);

            POA poa = POAHelper.narrow( orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();

            // Utworzenie instancji serwanta
            OrderServant orderServant = new OrderServant();

            org.omg.CORBA.Object o = poa.servant_to_reference(orderServant);

//            saveRefAsFile(orb, o, "ref.ior");

        saveRefAsFile(orb, o, "OrderSystem");

            java.lang.Object sync = new java.lang.Object();
            synchronized (sync) {
                sync.wait();
            }



    }

    private static void saveRefAsFile(ORB orb, org.omg.CORBA.Object ref, String refName) throws Exception {
      // uzyskanie referencji do usługi nazwowej
       org.omg.CORBA.Object o = orb.resolve_initial_references("NameService");

       //rzutowanie obiektu CORBA na obiekt Java
       NamingContextExt rootContext = NamingContextExtHelper.narrow(o);

       //tworzenie komponentu nazwowegi
       NameComponent nc = new NameComponent(refName, "");
       // tworzenie ścieżki nazwowej
       NameComponent path[] = {nc};
       // rejestracja obiektu w usłudze nazwowej pod daną ścieżką
       rootContext.rebind(path, ref);
    }
}

