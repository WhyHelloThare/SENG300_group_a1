package testing;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import main.Main;
import main.Types;

public class MainTest {
	
	/**
	 * Checks that parse method parses string and counts reference to ints.
	 */
	@Test
	public void testParseForIntTypeReference() {
		String type = "int";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nint a;\nint b;}", testType, type);
		assertEquals(2, testType.references);
	}
	
	/**
	 * Checks that parse method parses string and counts declarations of ints.
	 */
	@Test
	public void testParseForIntTypeDeclarations() {
		String type = "int";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nint a;\nint b;}", testType, type);
		assertEquals(0, testType.declarations);
	}
	
	@Test
	public void testParseForBooleanTypeReference() {
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nint b;}", testType, type);
		assertEquals(1, testType.references);
		
	}
	
	@Test
	public void testParseForBooleanTypeDeclarations() {
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nint b;}", testType, type);
		assertEquals(0, testType.declarations);
		
	}
	
	@Test
	public void testParseForStringTypeReference() {
		String type = "String";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\n String b;}", testType, type);
		assertEquals(1, testType.references);
	}
	
	@Test
	public void testParseForClassTypeReference() {
		String type = "B";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nprivate class B {}\nB b = new B();b = b;}", testType, type);
		assertEquals(2, testType.references);
	}
	
	@Test
	public void testParseForClassTypeDeclarations() {
		String type = "A";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nprivate class B {}\nB b = new B();b = b;}", testType, type);
		assertEquals(1, testType.declarations);
	}
	
	@Test
	public void testParseFilesInDirForDirWithJavaFilesForReference() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String dirPath = BASEDIR + "testFiles";
		String type = "int";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
		assertEquals(4, testType.references);

	}
	
	@Test
	public void testParseFilesInDirForDirWithJavaFilesForDeclarations() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String dirPath = BASEDIR + "testFiles";
		String type = "int";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
		assertEquals(0, testType.declarations);
	}
	
	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForDirNoFilesForReference() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String dirPath = BASEDIR + "empty";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForDirNoFilesForDeclarations() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String dirPath = BASEDIR + "empty";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);		
	}

	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForInvalidFolderForReference() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String dirPath = BASEDIR + "Invalid";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
	}
	
	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForInvalidFolderForDeclarations() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String dirPath = BASEDIR + "Invalid";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
	}
	
	@Test
	public void testreadFileToStringForValidFile() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String filePath = BASEDIR + "testFiles\\helloTest.txt";
		String actual	= Main.readFileToString(filePath);
		String expected = "hello its me!";
		
		assertEquals(expected, actual);
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testreadFileToStringForInvalidFile() throws IOException {
		String BASEDIR = "C:\\Users\\Masroor Hussain Syed\\Desktop\\UCalgary Courses\\Seng 300\\Assignment\\Assign 1\\src\\testing\\";
		String filePath = BASEDIR + "testFiles\\invalid.txt";
		String actual	= Main.readFileToString(filePath);
		String expected = "hello its me!";
	}
	
	
	
}
