import React, {forwardRef, useImperativeHandle, useRef} from "react";
import {Editor} from '@monaco-editor/react';

interface CodeEditorProps {
    value: string;
}

export const CodeEditor = forwardRef((props:CodeEditorProps, ref: any) => {
    const code = props.value?props.value:'// 请输入代码';
    console.log(code);

    const editorRef = useRef(null);

    function handleEditorDidMount(editor: any, monaco: any) {
        editorRef.current = editor;
    }


    useImperativeHandle(ref, () => ({
        getValue: () => {
            //@ts-ignore
            return editorRef.current?.getValue();
        }
    }));

    return (
        <Editor
            height="30vh"
            theme="vs-dark"
            defaultLanguage="javascript"
            defaultValue={code}
            onMount={handleEditorDidMount}
        />
    )
});