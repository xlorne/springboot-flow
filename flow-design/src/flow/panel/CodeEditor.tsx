import React, {forwardRef, useEffect, useImperativeHandle, useRef, useState} from "react";
import {Editor} from '@monaco-editor/react';
import {Modal} from "antd";

interface CodeEditorProps {
    value: string;
}

const MonacoEditor = forwardRef((props: CodeEditorProps, ref: any) => {
    const code = props.value ? props.value : '// 请输入代码';
    const editorRef = useRef(null);

    function handleEditorDidMount(editor: any, monaco: any) {
        editorRef.current = editor;
        try {
            editor.setValue(code);
        } catch (e) {
            editor.setValue('// 请输入代码');
        }
    }

    useImperativeHandle(ref, () => ({
        getValue: () => {
            //@ts-ignore
            return editorRef.current?.getValue();
        }
    }));

    return (
        <Editor
            height="60vh"
            theme="vs-dark"
            defaultLanguage="javascript"
            onMount={handleEditorDidMount}
        />
    )
});

interface CodeModelProps {
    onChange: (value: string) => void;
    show: boolean;
    value: string;
}

export const CodeEditor: React.FC<CodeModelProps> = (props) => {

    const [showCode, setShowCode] = useState(false);
    const codeEditorRef = useRef(null);
    const code = props.value ? props.value : '// 请输入代码';

    useEffect(() => {
        setShowCode(props.show);
    }, [props]);

    return (
        <Modal
            open={showCode}
            width={"80vw"}
            onCancel={() => {
                // @ts-ignore
                const code = codeEditorRef.current?.getValue();
                props.onChange(code);
                setShowCode(false);
            }}
            destroyOnClose={true}
            okText="确认"
            cancelText="取消"

            title="自定义代码"
            onOk={() => {
                // @ts-ignore
                const code = codeEditorRef.current?.getValue();
                props.onChange(code);
                setShowCode(false);
            }}
        >
            <MonacoEditor value={code} ref={codeEditorRef}/>
        </Modal>
    )
}