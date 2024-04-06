

export async function createElement(tag, attributes, ...children) {

    const element = document.createElement(tag);

    attributes = await attributes;

    if (isElement(attributes) || typeof attributes === "string")
        appendChild(element, attributes);

    else if (attributes != null && typeof attributes === "object")
        setAttributes(element, attributes);

    else if (attributes != null)
        throw new LogError("Invalid attributes for createElement");

    for (let child of children) {
        child = await child;

        if (child != null && (isElement(child) || typeof child === "string"))
            appendChild(element, child);

        else if (child != null)
            throw new LogError("Invalid child:", child, "for element:", element);
    }

    return element;
}


function appendChild(element, child) {
    if (typeof child === "string")
        element.appendChild(document.createTextNode(child));
    else
        element.appendChild(child);
}


function setAttributes(element, attributes) {
    for (const attribute in attributes) {
        if (attribute == null)
            continue;

        const value = attributes[attribute];
        if (value == null)
            continue;

        switch (attribute) {
            case "onClick":
                element.addEventListener("click", value);
                break;
            case "onSubmit":
                element.addEventListener("submit", value);
                break;
            case "onInvalid":
                element.addEventListener("invalid", value);
                break;
            case "onChange":
                element.addEventListener("change", value);
                break;
            case "onInput":
                element.addEventListener("input", value);
                break;
            case "style":
                for (const style in value)
                    element.style[style] = value[style];
                break;
            case "ref":
                value.resolve(element);
                break;
            default:
                element.setAttribute(attribute, value);
        }
    }
}

function isElement(obj) {
    return (
        typeof HTMLElement === "object" ? obj instanceof HTMLElement :
            obj && typeof obj === "object" && obj.nodeType === 1 && typeof obj.nodeName === "string"
    );
}