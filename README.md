# stencil-maven-plugin

Maven plugin wrapper over clojure [stencil](https://github.com/davidsantiago/stencil) library.

## Plugin configuration

### Build declaration example
Configuration example:
```
<plugin>
    <groupId>net.fiendishplatypus</groupId>
    <artifactId>stencil-maven-plugin</artifactId>
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
        <template>README.md.tpl</template>
        <context>README.md.ctx</context>
        <output>README.md</output>
    </configuration>
</plugin>
```
You need to explicitly provide `dependencies` on clojure and stencil.

### Configuration parameters

`configuration/template` - [Mustache](https://mustache.github.io/) template file.

`configuration/context` - context file. EDN map.

`configuration/output` - output file

## Using maven properties in templates

To use maven property in a template you need to replace `.` (dot) symbols to `_`
(underscore) in the template variable name. For example, in the template:

`maven.compiler.source is {{ maven_compiler_source }}`

template variable `maven_compiler_source` will take value from maven property `maven.compiler.source` 

Variables defined in `configuration/context` file take precedence over maven properties. 

## Goals

`renderFile` default goal. Template file using context from context
file and writes output to the output.