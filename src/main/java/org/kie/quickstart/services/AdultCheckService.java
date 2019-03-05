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

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.kie.quickstart.model.Adult;
import org.kie.quickstart.model.Person;
import org.drools.core.RuleBaseConfiguration;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.model.Model;
import org.drools.model.Rule;
import org.drools.model.Variable;
import org.drools.model.impl.ModelImpl;
import org.drools.modelcompiler.KiePackagesBuilder;
import org.drools.modelcompiler.builder.KieBaseBuilder;
import org.kie.api.runtime.KieSession;

import static org.drools.model.DSL.declarationOf;
import static org.drools.model.DSL.on;
import static org.drools.model.PatternDSL.pattern;
import static org.drools.model.PatternDSL.rule;

@ApplicationScoped
public class AdultCheckService {
    final KieSession ksession;

    AdultCheckService() {
        Variable<Person> pv = declarationOf(Person.class );

        Rule adult = rule("adult" ).build(
                        pattern(pv).expr(p -> p.getAge() > 18),
                        on(pv).execute(p -> session().insert(new Adult(p))));

        Model model = new ModelImpl().addRule(adult);
        RuleBaseConfiguration kieBaseConf = new RuleBaseConfiguration();
        KiePackagesBuilder builder = new KiePackagesBuilder(kieBaseConf);
        builder.addModel( model );
        InternalKnowledgeBase kieBase = new KieBaseBuilder(kieBaseConf).createKieBase(builder.build());

        ksession = kieBase.newKieSession();

    }

    KieSession session() {
        return ksession;
    }

    public void post(Person p) {
        ksession.insert(p);
        ksession.fireAllRules();
    }

    public List<Person> adults() {
        return ksession.getObjects(p -> p instanceof Adult)
                .stream().map(p -> ((Adult)p).getPerson())
                .collect(Collectors.toList());
    }

    public List<Person> persons() {
        return ksession.getObjects(p -> p instanceof Person)
                .stream().map(p -> ((Person)p))
                .collect(Collectors.toList());
    }
}
