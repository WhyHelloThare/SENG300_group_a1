import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Main {
	
	public static String BASEDIR = "C:\\Users\\jicka_000\\eclipse-workspace\\SENG300_group_a1\\src\\";
	public static String dirPath;

	public static void main(String[] args) {
		ArrayList<Types> allTypes = new ArrayList<Types>();
		
		try {
			dirPath = BASEDIR + args[0];
			allTypes = ParseFilesInDir(dirPath,allTypes);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error with filename");
		} catch (IOException ioe) {
			System.out.println("Error with IO");
		}
		
		for (Types aType : allTypes) {
			System.out.println("Type: " + aType.name + 
					", Declarations: " + aType.declarations + 
					", References: " + aType.references);
		}
		

	}
	
	//use ASTParse to parse string
	public static ArrayList<Types> parse(String str, ArrayList<Types> allTypes) {
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setSource(str.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
			
			Set<String> names = new HashSet<String>();
			
			// Class declarations
			public boolean visit(TypeDeclaration node) {
				String name = node.getName().getIdentifier();
				Types newType = new Types(name,1,0);
				allTypes.add(newType);
				
//				System.out.println("Declaration of '" + name);
				return true; // do not continue 
			}
			
			// Variable declarations
			public boolean visit(VariableDeclarationFragment node) {
				String name = node.getName().getIdentifier();
				
				boolean typeExists = false;
				// If type already exists add to declaration count
				for (Types aType : allTypes) {
					if (aType.name.matches(name)) {
						aType.declarations += 1;
						typeExists = true;
					}
				}
				// If type is new add new type to list
				if (!typeExists) {
					Types newType = new Types(name,1,0);
					allTypes.add(newType);
				}
				
//				System.out.println("Declaration of '" + name);
				return false; // do not continue 
			}
			
			// Calls to all declarations
			public boolean visit(SimpleName node) {
				String existingType = node.getIdentifier();
				// If type has been declared earlier in file
				for (Types aType : allTypes) {
					if (aType.name.matches(existingType)) {
						aType.references += 1;
//						System.out.println("Usage of '" + node);
					}
				}	
				return true;
			}
		});
		
		return allTypes;
	}
	
	
 
	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return fileData.toString();	
	}
 
	//loop directory to get file list
	public static ArrayList<Types> ParseFilesInDir(String dirPath, ArrayList<Types> allTypes) throws IOException{ 
		File root = new File(dirPath);
		File[] files = root.listFiles();
		String filePath = null;
 
		 for (File f : files ) {
			 filePath = f.getAbsolutePath();
			 if(f.isFile()){
				 // Get new types from each file and add to allTypes list
				 allTypes = parse(readFileToString(filePath),allTypes);
//				 for (Types aType : newTypes) {
//					 allTypes.add(aType);
//				 }
			 }
		 }
		 
		 return allTypes;
	}

}
