[![bintray-version](https://img.shields.io/bintray/v/muhanga/stencil-maven-plugin/stencil-maven-plugin?style=flat-square)](https://bintray.com/muhanga/stencil-maven-plugin/stencil-maven-plugin/0.0.3)
# stencil-maven-plugin 


Maven plugin wrapper over clojure [stencil](https://github.com/davidsantiago/stencil) library.

## Plugin configuration

### Build declaration example
Configuration example:
```
<plugin>
    <groupId>net.fiendishplatypus</groupId>
    <artifactId>stencil-maven-plugin</artifactId>
    <version>0.0.3</version>
    <dependencies>
        <dependency>
            <groupId>org.clojure</groupId>
            <artifactId>clojure</artifactId>
            <version>1.10.0</version>
        </dependency>
        <dependency>
            <groupId>stencil</groupId>
            <artifactId>stencil</artifactId>
            <version>0.5.0</version>
        </dependency>
    </dependencies>
    <configuration>
        <renderFiles>
            <renderFile>
                <template>README.md.tpl</template>
                <context>README.md.ctx</context>
                <output>README.md</output>
            </renderFile>
        </renderFiles>
    </configuration>
</plugin>
```
You need to explicitly provide `dependencies` for both clojure and stencil.
Template, context and output files configured inside `renderFile` tags.

### Configuration parameters

`configuration/template` - [Mustache](https://mustache.github.io/) template file.

`configuration/context` - context file. EDN map where keys are template variables
and values are template values. Both strings and keywords can be used for keys.

`configuration/output` - output file

## Using maven properties in templates

To use maven property in a template you need to replace `.` (dot) symbols to `_`
(underscore) in the template variable name. For example, in the template:

`maven.compiler.source is {{ maven_compiler_source }}`

template variable `maven_compiler_source` will take value from maven property `maven.compiler.source`

Variables defined in `configuration/context` file take precedence over maven properties.

See `doc/README.md` which is used to create this readme using this plugin. 

## Goals

`renderFile` create the file based on a `template`. Uses context from `context`
file and writes output to the `output`.

# How to release
Simple release: 

`mvn release:prepare --batch-mode -DpushChanges=false`

Install the newly released version locally for 
`mvn install`

Regenerate README.md:

`mvn stencil:renderFile`

Deploy to GitHub package:
`mvn deploy`

# License

Licensed under EPL 2.0
