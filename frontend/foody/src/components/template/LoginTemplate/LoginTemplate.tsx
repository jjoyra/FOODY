import React, { ReactNode } from 'react';
import './LoginTemplate.scss';
import LayoutPadding from 'constants/Padding';
import Layout from '../Layout/Layout';

function LoginTemplate({ children }: { children: ReactNode[] }) {
	return (
		<Layout padding={LayoutPadding.md}>
			<div className="main-div">
				<div className="main-logo">{children[0]}</div>
			</div>
			<div>{children[1]}</div>
		</Layout>
	);
}

export default LoginTemplate;