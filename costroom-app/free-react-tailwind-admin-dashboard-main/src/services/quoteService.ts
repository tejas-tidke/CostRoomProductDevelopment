// Use relative paths for NGINX reverse proxy
const API_BASE = "";

export async function createQuote(payload: any) {
  const response = await fetch(`/api/quotes`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error("Failed to create quote");
  }

  return await response.json();
}