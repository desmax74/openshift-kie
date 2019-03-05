/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.quickstart.services;

import javax.enterprise.context.ApplicationScoped;

import org.kie.quickstart.model.Person;
import org.drools.core.RuleBaseConfiguration;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.model.Index;
import org.drools.model.Model;
import org.drools.model.Rule;
import org.drools.model.Variable;
import org.drools.model.impl.ModelImpl;
import org.drools.modelcompiler.KiePackagesBuilder;
import org.drools.modelcompiler.builder.KieBaseBuilder;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import static org.drools.model.DSL.*;
import static org.drools.model.PatternDSL.*;

@ApplicationScoped
public class HelloRuleService {

    final KieSession ksession;
    private String result;

    HelloRuleService() {
        Variable<Person> markV = declarationOf(Person.class );
        Variable<Person> olderV = declarationOf(Person.class );

        Rule r = rule("beta" )
                .build(
                        pattern(markV)
                                .expr("exprA", p -> p.getName().equals( "Mark" ),
                                      alphaIndexedBy(String.class, Index.ConstraintType.EQUAL, 1, p -> p.getName(), "Mark" ),
                                      reactOn( "name", "age" )),
                        pattern(olderV)
                                .expr("exprB", p -> !p.getName().equals("Mark"),
                                      alphaIndexedBy( String.class, Index.ConstraintType.NOT_EQUAL, 1, p -> p.getName(), "Mark" ),
                                      reactOn( "name" ))
                                .expr("exprC", markV, (p1, p2) -> p1.getAge() > p2.getAge(),
                                      betaIndexedBy( int.class, Index.ConstraintType.GREATER_THAN, 0, p -> p.getAge(), p -> p.getAge() ),
                                      reactOn( "age" )),
                        on(olderV, markV).execute((p1, p2) -> result = ( p1.getName() + " is older than " + p2.getName()))
                );


        Model model = new ModelImpl().addRule(r);
        RuleBaseConfiguration kieBaseConf = new RuleBaseConfiguration();
        KiePackagesBuilder builder = new KiePackagesBuilder(kieBaseConf);
        builder.addModel( model );
        InternalKnowledgeBase kieBase = new KieBaseBuilder(kieBaseConf).createKieBase(builder.build());

        ksession = kieBase.newKieSession();

    }

    public String run() {
        Person mark = new Person("Mark", 37);
        Person edson = new Person("Edson", 35);
        Person mario = new Person("Mario", 40);

        FactHandle markFH = ksession.insert(mark);
        FactHandle edsonFH = ksession.insert(edson);
        FactHandle marioFH = ksession.insert(mario);

        ksession.fireAllRules();
//        if (!"Mario is older than Mark".equals( result )) {
//            throw new IllegalStateException();
//        }
//
//        result =( null );
//        ksession.delete( marioFH );
//        ksession.fireAllRules();
//        if (result != null) {
//            throw new IllegalStateException();
//        }
//
//        mark.setAge( 34 );
//        ksession.update( markFH, mark, "age" );
//
//        ksession.fireAllRules();
//        if (!"Edson is older than Mark".equals( result )) {
//            throw new IllegalStateException();
//        }
//
        return result;

    }

}