package cml

fun TopLevelDecl.construct(): ExecEnvironment = this.fields.copy()