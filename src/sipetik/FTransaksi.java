/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipetik;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Chynthia
 */
//public class FTransaksi extends javax.swing.JInternalFrame implements internal
public class FTransaksi extends javax.swing.JInternalFrame{
    koneksi k = new koneksi();    
    Date date;
    SimpleDateFormat s;
    DefaultTableModel tabModel;
    String a,b,c,d,e,f,g,h,klik;
    int stok, jlhbeli, bayar, kembali, total, subtotal, jml;
    /**
     * Creates new form FTransaksi
     */
    public FTransaksi() {
        initComponents();
        setTanggal();
    }

    void setTanggal(){
        date  = GregorianCalendar.getInstance().getTime();
        s = new SimpleDateFormat("dd MMM yyyy HH:mm");
        tfTanggal.setText(s.format(date));
        setKodeTransaksi();
        showBatik();
    }

    void setKodeTransaksi(){
        date  = GregorianCalendar.getInstance().getTime();
        s = new SimpleDateFormat("yyyyMMdd");
        
        try{
           k.query="select MAX(RIGHT(kode_transaksi, 4)) AS no FROM tbtransaksi";
           k.getData();
           while(k.rs.next()){
               if (k.rs.first()==false){
                   tfID.setText(s.format(date)+"TR000001");
               }
               else{
                   k.rs.last();
                   int id=k.rs.getInt(1)+1;
                   String no = String.valueOf(id);
                   
                   switch(no.length()){
                       case 1:
                            no ="00000" + id;
                            break;
                        case 2:
                            no ="0000" + id;
                            break;
                        case 3:
                            no ="000" + id;
                            break;
                        case 4:
                            no ="00" + id;
                            break;
                        case 5:
                            no ="0" + id;
                            break;
                        case 6:
                            no ="" + id;
                            break;
                   }
                   tfID.setText(s.format(date)+"TR"+no);
               }
           }
        }
        catch(SQLException ex){
            System.out.println("Error: "+ex+" setKode");
        }
    }

    void simpanDetail(){
        try{
            k.query= "SELECT * FROM tbtemporary";
            k.getData();
            while(k.rs.next()){
                a=k.rs.getString("kode_batik");
                b=k.rs.getString("nama_batik");
                c=k.rs.getString("harga");
                d=k.rs.getString("jml_beli");
                e=k.rs.getString("subtotal");

                k.query= "INSERT INTO tbdetail VALUES (?,?,?,?,?,?)";
                k.setData();
                k.ps.setString(1, tfID.getText());
                k.ps.setString(2, a);
                k.ps.setString(3, b);
                k.ps.setString(4, c);
                k.ps.setString(5, d);
                k.ps.setString(6, e);
                k.ps.executeUpdate();
            }
            k.rs.close();
        }catch(SQLException e){
            System.out.println("Error "+e+" detail");
        }
    }

    void hapusDetail(){
        try { 
           k.query="delete from tbdetail where kode_transaksi='"
                   +tfID.getText()+"'";
         
          k.setData();
          k.ps.executeUpdate();
           
       } catch(SQLException ex) { 
           System.out.println("error" +ex);
       }
    }
    
    void editDetail(){
        try{
           k.query="update tbdetail set kode_transaksi=?, kode_batik=?, nama_batik=?, harga_batik=?, jumlah_beli=?, subtotal=?  where kode_transaksi='"
                   +tfID.getText()+"'";
           k.setData();
           k.ps.setString(1, tfID.getText());
           k.ps.setString(2, tfKodeBatik.getText());
           k.ps.setString(3, tfNamaBatik.getText());
           k.ps.setString(4, tfHargaBatik.getText());
           k.ps.setString(5, tfJumlahBeliT.getText());
           k.ps.setString(6, tfSubtotal.getText());
           
          k.ps.executeUpdate();
           
       } catch(SQLException ex) { 
           System.out.println("error" +ex);
       }
    }
   
    void showBatik(){
        try{
            tableTransaksi.setDefaultEditor(Object.class, null);
            Object[] row ={"Kode Batik", "Nama Batik", "Stok", "Harga Jual"};
            tabModel = new DefaultTableModel(row,0);
            tableTransaksi.setModel(tabModel);
            k.query="SELECT tbbatik.kode_batik, tbbatik.nama_batik, tbbatik.harga_jual, tbstok.stok FROM tbbatik INNER JOIN tbstok WHERE tbbatik.kode_batik=tbstok.kode_batik AND tbstok.stok>0"; 
            k.getData();
            while (k.rs.next()) {
                  a=k.rs.getString("kode_batik");
                  b=k.rs.getString("nama_batik");
                  c=k.rs.getString("stok");
                  d=k.rs.getString("harga_jual");
                  
                  String[] data={a,b,c,d};
                  tabModel.addRow(data);
              }
        }
        catch(SQLException ex){
            System.out.println("Error: "+ex+" showBatik");
        }
    }
    
    void showBelanja(){
        try{
            tableBelanja.setDefaultEditor(Object.class, null);
            Object[] row ={"Kode Batik", "Nama Batik", "Harga", "Jumlah Beli", "Subtotal"};
            tabModel = new DefaultTableModel(row,0);
            tableBelanja.setModel(tabModel);
            k.query="SELECT * FROM tbtemporary"; 
            k.getData();
            while (k.rs.next()) {
                  a=k.rs.getString("kode_batik");
                  b=k.rs.getString("nama_batik");
                  c=k.rs.getString("harga");
                  d=k.rs.getString("jml_beli");
                  e=k.rs.getString("subtotal");
                  
                  String[] data={a,b,c,d,e};
                  tabModel.addRow(data);
              }
        }
        catch(SQLException ex){
            System.out.println("Error: "+ex+" showBelanja");
        }
    }
    
    public void clear(){
        tfKodeBatik.setText("");
        tfNamaBatik.setText("");
        tfHargaBatik.setText("");
        tfJumlahBeliT.setText("");
        tfSubtotal.setText("");
    }

    public void refreshTable() {
        k.getData();
        //Menghapus seluruh data
        tabModel.getDataVector().removeAllElements();

        //Memberi tahu bahwa telah kosong
        tabModel.fireTableDataChanged();
    }

    void hapusTemporary(){
        try { 
                  k.query="delete from tbtemporary";
                  k.setData();
                  k.ps.executeUpdate();
                  jNull.setText("");
                  tfBayar.setText("");
                  tfKembali.setText("");
               } catch(SQLException ex) { 
                   System.out.println("error" +ex);
               }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        tfCariBatik = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        tfID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfTanggal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfKodeKasir = new javax.swing.JTextField();
        tfNamaKasir = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tfKodeBatik = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfNamaBatik = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfHargaBatik = new javax.swing.JTextField();
        tfJumlahBeliT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tfSubtotal = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableBelanja = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        tfBayar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tfKembali = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jNull = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel13.setText("CARI DATA BATIK");

        tfCariBatik.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tfCariBatik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCariBatikKeyReleased(evt);
            }
        });

        tableTransaksi.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode Batik", "Nama Batik", "Stok", "Harga Jual"
            }
        ));
        tableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableTransaksi);

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel3.setText("Kode Transaksi");

        tfID.setEditable(false);
        tfID.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal Transaksi");

        tfTanggal.setEditable(false);
        tfTanggal.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tfTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTanggalActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel5.setText("Kode Kasir");

        tfKodeKasir.setEditable(false);
        tfKodeKasir.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        tfNamaKasir.setEditable(false);
        tfNamaKasir.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tfNamaKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaKasirActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel6.setText("Nama Kasir");

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel7.setText("Kode Batik");

        tfKodeBatik.setEditable(false);
        tfKodeBatik.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel8.setText("Nama Batik");

        tfNamaBatik.setEditable(false);
        tfNamaBatik.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel9.setText("Harga Batik");

        tfHargaBatik.setEditable(false);
        tfHargaBatik.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        tfJumlahBeliT.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tfJumlahBeliT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfJumlahBeliTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfJumlahBeliTKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel10.setText("Jumlah Beli");

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel11.setText("Subtotal");

        tfSubtotal.setEditable(false);
        tfSubtotal.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        btnTambah.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambah.png"))); // NOI18N
        btnTambah.setBorderPainted(false);
        btnTambah.setContentAreaFilled(false);
        btnTambah.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambahon.png"))); // NOI18N
        btnTambah.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tambah.png"))); // NOI18N
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahMouseClicked(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btnHapus.setBorderPainted(false);
        btnHapus.setContentAreaFilled(false);
        btnHapus.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapuson.png"))); // NOI18N
        btnHapus.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/hapus.png"))); // NOI18N
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel14.setText("KERANJANG BELANJA");

        tableBelanja.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        tableBelanja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Batik", "Nama Batik", "Harga", "Jumlah Beli", "Subtotal"
            }
        ));
        tableBelanja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBelanjaMouseClicked(evt);
            }
        });
        tableBelanja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableBelanjaKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tableBelanja);

        btnSimpan.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan.png"))); // NOI18N
        btnSimpan.setBorderPainted(false);
        btnSimpan.setContentAreaFilled(false);
        btnSimpan.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpanon.png"))); // NOI18N
        btnSimpan.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/simpan.png"))); // NOI18N
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSimpanMouseEntered(evt);
            }
        });

        btnBatal.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/batal.png"))); // NOI18N
        btnBatal.setBorderPainted(false);
        btnBatal.setContentAreaFilled(false);
        btnBatal.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/batalon.png"))); // NOI18N
        btnBatal.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/batal.png"))); // NOI18N
        btnBatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBatalMouseClicked(evt);
            }
        });
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        tfBayar.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfBayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfBayarKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel12.setText("Uang Bayar");

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel15.setText("Kembali");

        tfKembali.setEditable(false);
        tfKembali.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        tfKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKembaliActionPerformed(evt);
            }
        });
        tfKembali.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfKembaliKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(47, 37, 25));

        jLabel16.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Rp.");

        jNull.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jNull.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel16)
                .addGap(43, 43, 43)
                .addComponent(jNull)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jNull))
                .addGap(24, 24, 24))
        );

        jLabel17.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 0, 0));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/DATA TRANSAKSI.png"))); // NOI18N

        btnPrint.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cetak.png"))); // NOI18N
        btnPrint.setBorderPainted(false);
        btnPrint.setContentAreaFilled(false);
        btnPrint.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cetakon.png"))); // NOI18N
        btnPrint.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cetak.png"))); // NOI18N
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnTambah)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHapus))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(tfKodeBatik, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(tfNamaBatik, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(tfHargaBatik, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(tfJumlahBeliT, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(tfSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel14)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jScrollPane2)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tfCariBatik, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(38, 38, 38)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfID, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(tfKodeKasir)
                                                .addComponent(tfNamaKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 38, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(btnPrint)
                        .addGap(63, 63, 63))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tfCariBatik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tfKodeKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNamaKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfKodeBatik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNamaBatik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfHargaBatik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfJumlahBeliT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambah)
                    .addComponent(btnHapus))
                .addGap(13, 13, 13)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel12)
                                            .addComponent(tfBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tfKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSimpan)
                                    .addComponent(btnBatal))))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTanggalActionPerformed

    private void tfNamaKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaKasirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaKasirActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBatalActionPerformed

    private void tfKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKembaliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKembaliActionPerformed

    private void tfCariBatikKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCariBatikKeyReleased
        try {
            Object[] kolom ={"Kode Batik", "Nama Batik" ,"Stok" , "Harga Jual"};
            tabModel= new DefaultTableModel(kolom,0);
            tableTransaksi.setModel(tabModel);
            k.query="select tbbatik.kode_batik, tbbatik.nama_batik, tbstok.stok, tbbatik.harga_jual FROM tbbatik  INNER JOIN tbstok on tbbatik.kode_batik=tbstok.kode_batik where tbbatik.kode_batik like '%"
                    +tfCariBatik.getText()+
                    "%' or tbbatik.nama_batik like '%"+tfCariBatik.getText()+
                    "%' or tbstok.stok like '%"+tfCariBatik.getText()+
                    "%' or tbbatik.harga_jual like '%"+tfCariBatik.getText()+"%'";
            k.getData();
            while (k.rs.next()) {
                a=k.rs.getString("kode_batik");
                b=k.rs.getString("nama_batik");
                c=k.rs.getString("stok");
                d=k.rs.getString("harga_jual");
                String[] data={a,b,c,d};
                tabModel.addRow(data);
            }   
        } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }//GEN-LAST:event_tfCariBatikKeyReleased

    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
        try {
            int row =tableTransaksi.getSelectedRow();
            klik=(tableTransaksi.getModel().getValueAt(row, 0).toString());
            k.query="select kode_batik, nama_batik, harga_jual FROM tbbatik  WHERE kode_batik='"+klik+"'";
            k.getData();
            if(k.rs.next()){
                tfKodeBatik.setText(k.rs.getString("kode_batik"));
                tfNamaBatik.setText(k.rs.getString("nama_batik"));
                tfHargaBatik.setText(k.rs.getString("harga_jual"));
            }
        } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }//GEN-LAST:event_tableTransaksiMouseClicked

    private void tfJumlahBeliTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahBeliTKeyTyped
        if(Character.isAlphabetic(evt.getKeyChar())){
            JOptionPane.showMessageDialog(null, "Only number", "Information", JOptionPane.INFORMATION_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_tfJumlahBeliTKeyTyped

    private void tfJumlahBeliTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJumlahBeliTKeyReleased
        int a = Integer.parseInt(tfJumlahBeliT.getText());
        if (a<=0){
            JOptionPane.showMessageDialog(null, "Jumlah beli harus lebih dari 0", "Info", JOptionPane.INFORMATION_MESSAGE);
            tfJumlahBeliT.setText("1");
        }
        else{
            int b = Integer.parseInt(tfHargaBatik.getText());
            int c = Integer.parseInt(tfJumlahBeliT.getText());
            int total = (b*c);
            tfSubtotal.setText(String.valueOf(total));
        }
    }//GEN-LAST:event_tfJumlahBeliTKeyReleased

    private void btnTambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseClicked
        try{
            k.query="SELECT stok from tbstok where kode_batik='"+tfKodeBatik.getText()+"'";
            k.getData();
            while(k.rs.next()){
                a = k.rs.getString("stok");
            }
            stok = Integer.parseInt(a);
            jlhbeli = Integer.parseInt(tfJumlahBeliT.getText());
            if(jlhbeli>stok){
                JOptionPane.showMessageDialog(null, "Stok tidak tersedia", "Info", JOptionPane.INFORMATION_MESSAGE);
                tfJumlahBeliT.setText("1");
            }
            else{
                k.query="INSERT INTO tbtemporary VALUES(?,?,?,?,?)";
                k.setData();
                k.ps.setString(1, tfKodeBatik.getText().toString());
                k.ps.setString(2, tfNamaBatik.getText().toString());
                k.ps.setString(3, tfHargaBatik.getText().toString());
                k.ps.setString(4, tfJumlahBeliT.getText().toString());
                k.ps.setString(5, tfSubtotal.getText().toString());
                k.ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil dimasukkan ke keranjang belanja", "Info", JOptionPane.INFORMATION_MESSAGE);
                showBelanja();
                k.query="select * from tbtemporary";
                k.getData();
                jml=0;
                while(k.rs.next()){
                    h = k.rs.getString("subtotal");
                    subtotal = Integer.parseInt(h);
                    jml+=subtotal;
                }
                jNull.setText(String.valueOf(jml));
                }
        }
        catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }//GEN-LAST:event_btnTambahMouseClicked

    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
       clear();
    }//GEN-LAST:event_btnHapusMouseClicked

    private void btnBatalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseClicked
        int aa = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus?", "Info", JOptionPane.YES_NO_OPTION);
        if(aa==JOptionPane.YES_OPTION){
            hapusTemporary();
            refreshTable();
            clear();
        }
    }//GEN-LAST:event_btnBatalMouseClicked

    private void btnSimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanMouseEntered

    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        try{
                k.query="INSERT INTO tbtransaksi VALUES(?,?,?,?,?,?,?)";
                k.setData();
                k.ps.setString(1, tfID.getText());
                k.ps.setString(2, tfTanggal.getText());
                k.ps.setString(3, tfKodeKasir.getText());
                k.ps.setString(4, tfNamaKasir.getText());
                k.ps.setString(5, jNull.getText());
                k.ps.setString(6, tfBayar.getText());
                k.ps.setString(7, tfKembali.getText());
                k.ps.executeUpdate();
                simpanDetail();
                JOptionPane.showMessageDialog(null, "Data transaksi berhasil disimpan", "Info", JOptionPane.INFORMATION_MESSAGE);
                clear();
                setKodeTransaksi();
                hapusTemporary();
                refreshTable();
                clear();
            }
        catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }//GEN-LAST:event_btnSimpanMouseClicked

    private void tableBelanjaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableBelanjaKeyReleased
        int a = Integer.parseInt(tfJumlahBeliT.getText());
        if (a<=0){
            JOptionPane.showMessageDialog(null, "Jumlah beli harus lebih dari 0", "Info", JOptionPane.INFORMATION_MESSAGE);
            tfJumlahBeliT.setText("");
        }
        else{
            int b = Integer.parseInt(tfHargaBatik.getText());
            int c = Integer.parseInt(tfJumlahBeliT.getText());
            total = (b*c);
            tfSubtotal.setText(String.valueOf(total));
        }
    }//GEN-LAST:event_tableBelanjaKeyReleased

    private void tfKembaliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfKembaliKeyReleased
        
    }//GEN-LAST:event_tfKembaliKeyReleased

    private void tfBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBayarKeyReleased
        bayar = Integer.parseInt(tfBayar.getText());
        kembali = bayar - jml;
        if(kembali>=0){
            tfKembali.setText(String.valueOf(kembali));
        }
        else{
            tfKembali.setText("-");
        }
    }//GEN-LAST:event_tfBayarKeyReleased

    private void tfBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBayarKeyTyped
        if(!Character.isDigit(evt.getKeyChar())){
            evt.consume();
        }
    }//GEN-LAST:event_tfBayarKeyTyped

    private void tableBelanjaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBelanjaMouseClicked
        try {
            int row =tableBelanja.getSelectedRow();
            klik=(tableBelanja.getModel().getValueAt(row, 0).toString());
            k.query="select * from tbtemporary where kode_batik='"+klik+"'";
            k.getData();
            if(k.rs.next()){
                a=k.rs.getString("kode_batik");
                tfKodeBatik.setText(a);
                b=k.rs.getString("nama_batik");
                tfNamaBatik.setText(b);
                c=k.rs.getString("harga");
                tfHargaBatik.setText(c);
                d=k.rs.getString("jml_beli");
                tfJumlahBeliT.setText(d);
                e=k.rs.getString("subtotal");
                tfSubtotal.setText(e);
            } } catch (SQLException ex) {
            System.out.println("Error "+ex);
        }
    }//GEN-LAST:event_tableBelanjaMouseClicked

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        try{
            String file = "/eReport/struk.jasper";
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file),null,k.conn());
            JasperViewer.viewReport(print, false);
            
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_btnPrintMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jNull;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableBelanja;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JTextField tfBayar;
    private javax.swing.JTextField tfCariBatik;
    private javax.swing.JTextField tfHargaBatik;
    private javax.swing.JTextField tfID;
    private javax.swing.JTextField tfJumlahBeliT;
    private javax.swing.JTextField tfKembali;
    private javax.swing.JTextField tfKodeBatik;
    public javax.swing.JTextField tfKodeKasir;
    private javax.swing.JTextField tfNamaBatik;
    public javax.swing.JTextField tfNamaKasir;
    private javax.swing.JTextField tfSubtotal;
    private javax.swing.JTextField tfTanggal;
    // End of variables declaration//GEN-END:variables
}
