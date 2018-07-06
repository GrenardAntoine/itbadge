package com.itescia.itbadge.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itescia.itbadge.domain.Authority;
import com.itescia.itbadge.domain.Groupe;
import com.itescia.itbadge.domain.User;
import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.repository.AuthorityRepository;
import com.itescia.itbadge.repository.CoursRepository;
import com.itescia.itbadge.repository.GroupeRepository;
import com.itescia.itbadge.repository.UserRepository;
import com.itescia.itbadge.repository.UtilisateurRepository;
import com.itescia.itbadge.service.dto.UserDTO;
import com.itescia.itbadge.service.impl.CoursServiceImpl;
import com.itescia.itbadge.service.impl.GroupeServiceImpl;
import com.itescia.itbadge.service.impl.UtilisateurServiceImpl;
import com.itescia.itbadge.service.util.RandomUtil;
import com.itescia.itbadge.web.rest.UserResource;
import com.itescia.itbadge.web.rest.UtilisateurResource;

@Service
public class UploadService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private static final String Upload_User_FILE_PATH = "C:\\Users\\Itescia\\git\\itbadge\\user\\uploadUser.xlsx";
    private static final String Upload_Cours_FILE_PATH = "C:\\Users\\Itescia\\git\\itbadge\\user\\";
    
    @Autowired
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    private final CoursRepository coursRepository;

    @Autowired
    private final GroupeRepository groupeRepository;
    
    
    public UploadService(UtilisateurRepository uR, CoursRepository cR, GroupeRepository gR) {
    	this.utilisateurRepository = uR;
    	this.coursRepository = cR;
    	this.groupeRepository = gR;
    }
        
    
    public void uploadCours()
    {
    	
    }
    
    public void uploadUtilisateur() throws IOException, EncryptedDocumentException, InvalidFormatException
    {
    	log.debug("Ajout des utilisateurs à la BDD");
    	 // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(Upload_User_FILE_PATH));

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        Iterator<Row> rowIterator = sheet.rowIterator();
        int i = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            	if (i!=0)
            	{
            		ArrayList<String> listUtilisateur = new ArrayList<String>();
		            // Now let's iterate over the columns of the current row
		            Iterator<Cell> cellIterator = row.cellIterator();
		            
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                String cellValue = dataFormatter.formatCellValue(cell);
		                listUtilisateur.add(cellValue);
		            }
		            UtilisateurService uS = new UtilisateurServiceImpl(utilisateurRepository, coursRepository, groupeRepository);
		            Utilisateur util = new Utilisateur();
		            User user = new User();
		            					
					System.out.println(listUtilisateur.get(2));
		            user.setLastName(listUtilisateur.get(0));
		            user.setFirstName(listUtilisateur.get(1));
		            user.setLogin(listUtilisateur.get(2));
		            user.setEmail(listUtilisateur.get(2)+"@edu.itescia.fr");
		            user.setActivated(true);
		            String encryptedPassword = new BCryptPasswordEncoder().encode(RandomUtil.generatePassword());;
		            user.setPassword(encryptedPassword);
		            Set<Authority> gA = new HashSet<Authority>();
		            Authority a = new Authority();
		            a.setName("ROLE_USER");
		            gA.add(a);
            		user.setAuthorities(gA);					
		            util.setUser(user);
		            util.setIsAdmin(false);
		            util.setIsProfesseur(false);
		            uS.save(util);
		            		            
		            System.out.println((uS.findOneByLogin(listUtilisateur.get(2).toString())).isPresent());
		            
		            System.out.println("");
            	}
            	i++;
        }

        // Closing the workbook
        workbook.close();

        linkUtilisateurGroupe();
    }
    
    
    public void linkUtilisateurGroupe() throws IOException, EncryptedDocumentException, InvalidFormatException {
    	
    	System.out.println("Insertion des groupes");
    	log.debug("Lien / ajout des groupes");
   	 // Creating a Workbook from an Excel file (.xls or .xlsx)
       Workbook workbook = WorkbookFactory.create(new File(Upload_User_FILE_PATH));

       // Getting the Sheet at index zero
       Sheet sheet = workbook.getSheetAt(0);

       // Create a DataFormatter to format and get each cell's value as String
       DataFormatter dataFormatter = new DataFormatter();

       // 1. You can obtain a rowIterator and columnIterator and iterate over them
       System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
       Iterator<Row> rowIterator = sheet.rowIterator();
       int i = 0;
       while (rowIterator.hasNext()) {
           Row row = rowIterator.next();
           	if (i!=0)
           	{
           		ArrayList<String> listUtilisateur = new ArrayList<String>();
		            // Now let's iterate over the columns of the current row
		            Iterator<Cell> cellIterator = row.cellIterator();
		            
		            while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                String cellValue = dataFormatter.formatCellValue(cell);
		                listUtilisateur.add(cellValue);
		            }
		            UtilisateurService uS = new UtilisateurServiceImpl(utilisateurRepository, coursRepository, groupeRepository);
		            Optional<Utilisateur> util = uS.findOneByLogin(listUtilisateur.get(2));
		            if(util.isPresent()) {
		            	final Utilisateur utilisateur = util.get();
		            	System.out.println(utilisateur.getGroupe());
		            	if(utilisateur.getGroupe() == null) {
		            		GroupeService gS = new GroupeServiceImpl(groupeRepository, new CoursServiceImpl(coursRepository, uS));
		            		Optional<Groupe> group = gS.findByName(listUtilisateur.get(3));
		            		//System.out.println(group.isPresent());
		            		 
		            		if(!group.isPresent()) {
		            			final Groupe groupeAdd = new Groupe();
		            			groupeAdd.setNom(listUtilisateur.get(3));
		            			gS.save(groupeAdd);
		            		}
		            		group = gS.findByName(listUtilisateur.get(3)); 
		            		utilisateur.setGroupe(group.get());
		            		uS.save(utilisateur);
		            		
		            	}
		            }
		            					
		            
		            System.out.println();
           	}
           	i++;
       }

       // Closing the workbook
       workbook.close();
    	
    }
    
}
