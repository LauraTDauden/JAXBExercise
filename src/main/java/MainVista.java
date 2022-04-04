import domain.Tienda;
import domain.Videojuego;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainVista {
    private JButton crearXMLButton;
    private JButton leerXMLButton;
    private JTextField text_titulo;
    private JTextField text_desarrollador;
    private JTextField text_anio;
    private JTextField text_genero;
    private JTable table1;
    private JPanel jpanel;

    Object  [] [] data = {};

    static Tienda tienda = new Tienda();
    static ArrayList<Videojuego> coleccionJuegos = new ArrayList<>();

    private static JAXBContext context;
    private static Marshaller m;

    private static final String TIENDA_XML = "./tienda-videjuegos.xml";

    public MainVista() {
        createTable();
        crearXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addJuego();
                createXML();
            }
        });
        leerXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    m.marshal(tienda, System.out);
                    readXML();
                } catch (JAXBException ex) {
                    ex.printStackTrace();
                }
                System.out.println("XML leído");
            }
        });
    }

    public static void setColeccionJuegos() {
        Unmarshaller um = null;
        try {
            um = context.createUnmarshaller();
            Tienda tienda2 = (Tienda) um.unmarshal(new FileReader(TIENDA_XML));
            coleccionJuegos= (ArrayList<Videojuego>) tienda2.getColeccionJuegos();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void createContext(){
        tienda.setNombre("GameShop");
        tienda.setColeccionJuegos(coleccionJuegos);
        tienda.setLocalizacion("Maspalomas");
        //CONTEXTO JAXB
        try{
            context = JAXBContext.newInstance(Tienda.class);
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            setColeccionJuegos();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    private void createTable(){
        table1.setModel(new DefaultTableModel(
            data,
        new String []{"Título", "Desarrolladora", "Año", "Género" }
        ));

    }

    private void createXML(){
        try {
            m.marshal(tienda, new File(TIENDA_XML));
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        System.out.println("XML creado");
    }

    private void addJuego(){
        Videojuego juego = new Videojuego();
        if(!text_titulo.getText().isBlank()){
            juego.setNombre(text_titulo.getText());
            juego.setDesarrolladora(text_desarrollador.getText());
            try{
                juego.setAnio(Integer.parseInt(text_anio.getText()));
            } catch (Exception e){
                juego.setAnio(0);
            }
            juego.setGenero(text_genero.getText());
            coleccionJuegos.add(juego);
        }
    }

    private void readXML(){
        Unmarshaller um = null;
        try {
            um = context.createUnmarshaller();
            Tienda tienda2 = (Tienda) um.unmarshal(new FileReader(TIENDA_XML));
            List<Videojuego> lista = tienda2.getColeccionJuegos();
            DefaultTableModel ml = (DefaultTableModel)table1.getModel();
            ml.setRowCount(0);
            int i = 0;
            for(Videojuego juego: lista){
                ml.addRow(new Object[]{juego.getNombre(), juego.getDesarrolladora(),
                        String.valueOf(juego.getAnio()), juego.getGenero()});
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void createInitialData(){

        //OBJETOS PARA CREAR EL XML

        Videojuego juego = new Videojuego();
        juego.setNombre("Elden Ring");
        juego.setDesarrolladora("FromSoftware");
        juego.setAnio(2022);
        juego.setGenero("Action RPG");
        coleccionJuegos.add(juego);

        Videojuego juego2 = new Videojuego();
        juego2.setNombre("Hades");
        juego2.setDesarrolladora("Supergiant games");
        juego2.setAnio(2020);
        juego2.setGenero("Roguelite");
        coleccionJuegos.add(juego2);
    }



    public static void main(String[] args) {
        //VISTA
        JFrame frame = new JFrame("Videjuegos");
        frame.setContentPane(new MainVista().jpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0, 0, 500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        createInitialData();
        createContext();



    }
}
