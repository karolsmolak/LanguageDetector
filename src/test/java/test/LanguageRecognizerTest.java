package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utility.LanguageRecognizer;

public class LanguageRecognizerTest {
	
	@Test
	public void testRecognize() {
		assertEquals("Polish", LanguageRecognizer.recognize(
				"Częściowo zachowany pysk przypomina należący do stegozaura swym znacznym tylnym wyrostkiem przedszczękowym oraz poszerzeniem podniebienia.").get(0).getMatchedLanguage().getName());
		assertEquals("Dutch", LanguageRecognizer.recognize(
				"Wilhelmus van Nassouwe\n" + 
				"Ben ick van Duytschen bloet,\n" + 
				"Den Vaderlant getrouwe\n" + 
				"Blyf ick tot in den doet:\n" + 
				"Een Prince van Oraengien\n" + 
				"Ben ick vrij onverveert,\n" + 
				"Den Coninck van Hispaengien\n" + 
				"Heb ick altijt gheeert.").get(0).getMatchedLanguage().getName());
		assertEquals("English", LanguageRecognizer.recognize(
				"When Paley became sole principal in 1856, he continued to work mainly on churches, designing new ones and restoring, rebuilding, and making additions and alterations to existing churches.").get(0).getMatchedLanguage().getName());
		assertEquals("French", LanguageRecognizer.recognize(
				"C'est à vingt-cinq ans qu'elle a bâti les premiers concepts et institutions de son univers sorcier, dans lequel un enfant orphelin découvrait à la fois son héritage tragique et ses talents de magicien").get(0).getMatchedLanguage().getName());
		assertEquals("German, Standard (1901)", LanguageRecognizer.recognize(
				"Die Schlacht, in der ein Achtel des Gesamtheeres des Römischen Reiches vernichtet wurde, leitete das Ende der römischen Bemühungen ein, die rechtsrheinischen Gebiete Germaniens bis zur Elbe (Fluvius Albis) zu einer Provinz des Römischen Reiches zu machen. ").get(0).getMatchedLanguage().getName());
		assertEquals("Italian", LanguageRecognizer.recognize(
				"Fu inoltre punto di riferimento del movimento socialista mondiale nell'ambito dell'Internazionale Comunista (1919-1943) e, dopo la vittoria nella seconda guerra mondiale, nel contesto della Guerra fredda. ").get(0).getMatchedLanguage().getName());
		assertEquals("Portuguese (Portugal)", LanguageRecognizer.recognize(
				"Durante sua juventude, Alexandre foi orientado pelo filósofo Aristóteles até os 16 anos. Depois que Filipe foi assassinado em 336 a.C., Alexandre sucedeu seu pai ao trono e herdou um reino forte e um exército experiente.").get(0).getMatchedLanguage().getName());
		assertEquals("Spanish", LanguageRecognizer.recognize(
				"La danza o el baile es la ejecución de movimientos acompasados con el cuerpo, los brazos y las piernas. ").get(0).getMatchedLanguage().getName());
		assertEquals("Latin", LanguageRecognizer.recognize(
				"Planeta extrasolaris repertus est, stellam HD 76920 constellationis Volantis circumiens, cuius orbita omnium excentrissima sit, apastro fere 2 unitatibus astronomicis a stella distante, periastro non pluribus quam 4 diametris stellae ipsius").get(0).getMatchedLanguage().getName());
		assertEquals("Turkish", LanguageRecognizer.recognize(
				"Lorem Ipsum, dizgi ve baskı endüstrisinde kullanılan mıgır metinlerdir.").get(0).getMatchedLanguage().getName());
		assertEquals("Ukrainian", LanguageRecognizer.recognize(
				"Lorem Ipsum - це текст-\"риба\", що використовується в друкарстві та дизайні. ").get(0).getMatchedLanguage().getName());
		assertEquals("Macedonian", LanguageRecognizer.recognize(
				"Lorem Ipsum е едноставен модел на текст кој се користел во печатарската индустрија.").get(0).getMatchedLanguage().getName());
		assertEquals("Latvian", LanguageRecognizer.recognize(
				"Lorem Ipsum – tas ir teksta salikums, kuru izmanto poligrāfijā un maketēšanas darbos.").get(0).getMatchedLanguage().getName());
		assertEquals("Hungarian", LanguageRecognizer.recognize(
				" Lorem Ipsum egy egyszerû szövegrészlete, szövegutánzata a betûszedõ és nyomdaiparnak. A Lorem Ipsum az 1500-as évek óta standard szövegrészletként ").get(0).getMatchedLanguage().getName());
		assertEquals("Lithuanian", LanguageRecognizer.recognize(
				"Lorem ipsum - tai fiktyvus tekstas naudojamas spaudos ir grafinio dizaino pasaulyje jau nuo XVI a.").get(0).getMatchedLanguage().getName());
		assertEquals("Russian", LanguageRecognizer.recognize(
				"Lorem Ipsum - это текст-\"рыба\", часто используемый в печати и вэб-дизайне.").get(0).getMatchedLanguage().getName());
		assertEquals("Albanian, Tosk", LanguageRecognizer.recognize(
				"Lorem Ipsum është një tekst shabllon i industrisë së printimit dhe shtypshkronjave.").get(0).getMatchedLanguage().getName());
		assertEquals("Vietnamese", LanguageRecognizer.recognize(
				"Lorem Ipsum chỉ đơn giản là một đoạn văn bản giả, được dùng vào việc trình bày và dàn trang phục vụ cho in ấn").get(0).getMatchedLanguage().getName());
	}

}