# stencil-maven-plugin

Maven plugin wrapper over clojure [stencil](https://github.com/davidsantiago/stencil) library.

## Plugin configuration

### Build declaration example
Configuration example:
```
<plugin>
    <groupId>net.fiendishplatypus</groupId>
    <artifactId>stencil-maven-plugin</artifactId>
    <version>{{version}}</version>
    <dependencies>
        <dependency>
            <groupId>org.clojure</groupId>
            <artifactId>clojure</artifactId>
            <version>{{version_clojure}}</version>
        </dependency>
        <dependency>
            <groupId>stencil</groupId>
            <artifactId>stencil</artifactId>
            <version>{{version_stencil}}</version>
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

{{=<% %>=}}
`maven.compiler.source is {{ maven_compiler_source }}`
<%={{ }}=%>

template variable `maven_compiler_source` will take value from maven property `maven.compiler.source`

Variables defined in `configuration/context` file take precedence over maven properties.

## Goals

`renderFile` create the file based on a `template`. Uses context from `context`
file and writes output to the `output`.

# License

Licensed under EPL 2.0