/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package owl.cs.owlparserdemo;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import java.io.File;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;

/**
 *
 * @author Burak
 */
public class OwlParserDemo {

    public static void main(String[] args) throws SWRLParseException, SWRLBuiltInException, OWLOntologyStorageException, OWLOntologyCreationException {
        // Load the ontology
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File("C:\\Users\\Burak\\Desktop\\Demo\\testOntology.owl"));


	SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
        
	SWRLAPIRule ruleOne = ruleEngine.createSWRLRule("TestKural", "untitled-ontology-19:Male(?x) ^ untitled-ontology-19:Male(?y) ^ untitled-ontology-19:Male(?z) ^ untitled-ontology-19:hasBrother(?x, ?y) ^ untitled-ontology-19:hasFather(?x, ?z) -> untitled-ontology-19:hasFather(?y, ?z)");
        
        manager.saveOntology(ontology, IRI.create(new File("C:\\Users\\Burak\\Desktop\\Demo\\testOntologySWRLKuralarIle.owl")));
        
	PelletReasonerFactory reasonerFactory = new PelletReasonerFactory();
	PelletReasoner reasoner = reasonerFactory.createReasoner(ontology);

	reasoner.getKB().realize();
        reasoner.getKB().classify();
        
	OWLOntology exportedOntology = manager.createOntology();
        InferredOntologyGenerator generator = new InferredOntologyGenerator( reasoner );
        generator.fillOntology(manager.getOWLDataFactory(), exportedOntology );

	manager.saveOntology(exportedOntology, IRI.create(new File("C:\\Users\\Burak\\Desktop\\Demo\\testOntologyInferred-EXTENDED(SON HALI).owl")));
    }
}
